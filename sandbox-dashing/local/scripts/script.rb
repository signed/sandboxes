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

  def to_s
    "#{@plan_name} #{@key} -> #{@state}"
  end
end

class BambooRestClient
  def initialize(host, port)
    @host = host
    @port = port
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
    "http://#{@host}:#{@port}/bamboo/rest/api/latest/"
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
  def plan_does_not_exist
    print 'plan not found'
  end

  def could_not_connect_to_bamboo(exception)
    print 'could not connect to bamboo'
    print exception
  end

  def branch_build_outcomes(build_outcomes)
    build_outcomes.each { |result| print result.to_s + "\n" }
  end
end

json_builder = JsonBuilder.new

bamboo_rest_client = BambooRestClient.new('localhost', 6990)
bamboo_rest_client.latest_build_outcome_for_all_branches('DAS-SAM', json_builder)
bamboo_rest_client.latest_build_outcome_for_all_branches('DAS-SAD', json_builder)

