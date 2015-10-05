# :first_in sets how long it takes before the job is first run. In this case, it is run immediately


#!/usr/bin/env ruby
require 'rest-client'
require 'net/https'
require 'json'

def pretty_print_json(json)
  print JSON.pretty_generate(json)
end

class BuildOutcome
  def initialize(plan_name, key, state)
    @plan_name = plan_name
    @key = key
    @state = state
  end

  def plan_name
    @plan_name
  end

  def failed?
    'Failed'.eql?(@state)
  end

  def to_s
    "#{@plan_name} #{@key} -> #{@state}"
  end
end

class BambooRestClient
  def initialize(protocol, host, port, path='')
    @protocol = protocol
    @host = host
    @port = port
    @path = path
  end

  def latest_build_outcome_for_all_branches(plan_key, build_outcome_listener)
    begin
      build_status_url = latest_rest_api+"result/#{plan_key}.json?expand=results[0].result.plan.branches.branch.latestResult"
      response = RestClient.get(build_status_url) { |response, _, _| response }
    rescue => e
      build_outcome_listener.could_not_connect_to_bamboo(e)
      return
    end

    if response.code == 404
      build_outcome_listener.plan_does_not_exist
      return
    end

    json = JSON.parse(response)
    all_branch_results(json, build_outcome_listener)
  end

  private

  def latest_rest_api
    "#{@protocol}://#{@host}:#{@port}#{@path}/rest/api/latest/"
  end

  def all_branch_results(json, build_outcome_listener)
    json_with_build_result_information = []
    master_branch_json = json['results']['result'][0]
    master_branch_json['planName'] = 'master'
    json_with_build_result_information << master_branch_json
    master_branch_json['plan']['branches']['branch'].each { |branch| json_with_build_result_information << branch['latestResult'] }
    build_outcome_listener.branch_build_outcomes(json_with_build_result_information.map { |json| extract_build_outcome_from_json json })
  end

  def extract_build_outcome_from_json(json)
    BuildOutcome.new json['planName'], json['key'], json['buildState']
  end
end

class JsonBuilder

  def initialize
    @json_dictionary = {
        :bamboo_not_reachable => false,
        :plan_does_not_exist => false,
        :failed_plans => []
    }
  end

  def plan_does_not_exist
    @json_dictionary[:plan_dos_not_exist] = true
  end

  def could_not_connect_to_bamboo(exception)
    @json_dictionary[:bamboo_not_reachable] = true
  end

  def branch_build_outcomes(build_outcomes)
    @json_dictionary[:failed_plans] = build_outcomes.select { |build_outcome| build_outcome.failed? }
                                          .map { |failed| failed_branch failed }
  end

  def json
    @json_dictionary
  end

  private
  def failed_branch(build_outcome)
    {
        :name => build_outcome.plan_name,
        :failed_because => 'just failed',
        :link => 'http://localhost:3030/sample'
    }
  end
end

def status_json
  json_builder = JsonBuilder.new
  bamboo_rest_client = BambooRestClient.new('http', 'http', 80, 'bamboo')
  bamboo_rest_client.latest_build_outcome_for_all_branches('DAS-SAM', json_builder)
  json_builder.json
end

if __FILE__==$0
  print status_json
else
  SCHEDULER.every '10s', allow_overlapping: false do |job|
    send_event('bamboo', status_json)
  end
end


