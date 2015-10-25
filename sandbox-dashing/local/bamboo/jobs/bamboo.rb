#!/usr/bin/env ruby

require 'rest-client'
require 'net/https'
require 'json'

#--------------------------------------------------------------------------------
# Configuration
#--------------------------------------------------------------------------------
configuration = {
    :bamboo_url => 'http://localhost:6990/bamboo',
    :rest_endpoint => '/rest/api/latest',
    :refresh_rate => '10s',
    :plan_keys => %w[AP-FAIL AP-SUCCEED].uniq
}
# trouble plan SRVCS-AUDIOV2MOCKS
#--------------------------------------------------------------------------------

def pretty_print_json(json)
  print JSON.pretty_generate(json)
end

class BuildOutcome
  def initialize(plan_name, key, state, enabled, result_key)
    @plan_name = plan_name
    @key = key
    @state = state
    @enabled = enabled
    @result_key = result_key
  end

  def plan_name
    @plan_name
  end

  def result_key
    @result_key
  end

  def successful?
    'Successful'.eql?(@state)
  end

  def failed?
    'Failed'.eql?(@state)
  end

  def enabled?
    @enabled
  end

  def to_s
    enabled_indicator = @enabled ? '+' : '-'
    "#{enabled_indicator} #{@plan_name} #{@key} -> #{@state}"
  end
end

class BambooUrl
  def initialize(base, rest_endpoint)
    @base = base
    @rest_endpoint = rest_endpoint
  end

  def build_status_url_for(plan_key)
    latest_rest_api+"/result/#{plan_key}.json?expand=results[0].result.plan.branches.branch.latestResult"
  end

  def web_url_to_build_result(result_key)
    @base + '/browse/' + result_key
  end

  def web_url_to_plan(plan_key)
    @base + '/browse/' + plan_key
  end

  private

  def latest_rest_api
    @base+@rest_endpoint
  end

end

class BambooRestClient
  def initialize(bamboo_url)
    @bamboo_url = bamboo_url
  end

  def latest_build_outcome_for_all_branches(plan_key, build_outcome_listener)
    begin
      url = @bamboo_url.build_status_url_for plan_key
      #print url + "\n"
      response = RestClient.get(url) { |response, _, _| response }
    rescue => e
      build_outcome_listener.could_not_connect_to_bamboo(e)
      return
    end

    if response.code == 404
      build_outcome_listener.plan_does_not_exist
      return
    end

    json = JSON.parse(response, symbolize_names: true)
    all_branch_results(json, build_outcome_listener)
  end

  private

  def all_branch_results(json, build_outcome_listener)
    json_with_build_result_information = []
    master_branch_json = json[:results][:result][0]
    master_branch_json[:planName] = 'master'
    json_with_build_result_information << master_branch_json unless master_branch_json.nil?
    master_branch_json[:plan][:branches][:branch].each do |branch|
      latest_result = branch[:latestResult]
      json_with_build_result_information << latest_result unless latest_result.nil?
    end
    build_outcome_listener.branch_build_outcomes(json_with_build_result_information.map { |branch_json| extract_build_outcome_from_json branch_json })
  end

  def extract_build_outcome_from_json(json)
    #pretty_print_json json
    BuildOutcome.new json[:planName], json[:key], json[:buildState], json[:plan][:enabled], json[:planResultKey][:key]
  end
end

class JsonBuilder

  def initialize(bamboo_url, plan_key)
    @bamboo_url = bamboo_url
    @json_dictionary = {
        :plan_url => (bamboo_url.web_url_to_plan plan_key),
        :bamboo_not_reachable => false,
        :plan_does_not_exist => false,
        :failed_plans => []
    }
  end

  def plan_does_not_exist
    @json_dictionary[:plan_does_not_exist] = true
  end

  def could_not_connect_to_bamboo(exception)
    @json_dictionary[:bamboo_not_reachable] = true
  end

  def branch_build_outcomes(build_outcomes)
    #build_outcomes.each{|outcome| print outcome.to_s + "\n"}
    failed_branches_to_report = build_outcomes.select { |build_outcome| not (build_outcome.successful?) }
                                    .select { |build_outcome| build_outcome.enabled? }
                                    .map { |failed| failed_branch failed }
    @json_dictionary[:failed_plans] = failed_branches_to_report
  end

  def json
    @json_dictionary
  end

  private
  def failed_branch(build_outcome)
    {
        :name => build_outcome.plan_name,
        :failed_because => 'just failed',
        :link => (@bamboo_url.web_url_to_build_result build_outcome.result_key)
    }
  end
end

def status_json_for(plan_key, bamboo_url)
  json_builder = JsonBuilder.new(bamboo_url, plan_key)
  bamboo_rest_client = BambooRestClient.new(bamboo_url)
  bamboo_rest_client.latest_build_outcome_for_all_branches(plan_key, json_builder)
  json_builder.json
end

run_as_script = __FILE__==$0

configuration[:plan_keys].map { |plan_key| {:plan_key => plan_key, :run_as_script => run_as_script} }.each do |argument|
  bamboo_url = BambooUrl.new(configuration[:bamboo_url], configuration[:rest_endpoint])
  plan_key = argument[:plan_key]
  if argument[:run_as_script]
    pretty_print_json status_json_for(plan_key, bamboo_url)
  else
    SCHEDULER.every configuration[:refresh_rate], allow_overlapping: false do |job|
      send_event(plan_key, status_json_for(plan_key, bamboo_url))
    end
  end
end

