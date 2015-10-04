#!/usr/bin/env ruby
require 'rest-client'
require 'net/https'
require 'json'

response = RestClient.get 'http://localhost:6990/bamboo/rest/api/latest/result/DAS-SAM.json?expand=results%5B0%5D.result.plan.branches.branch.latestResult'

json = JSON.parse(response)

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

def extract_build_outcome_from_json(json)
  BuildOutcome.new json['planName'], json['key'], json['buildState']
end


def all_branch_results(json)
  json_with_build_result_information = []
  master_branch_json = json['results']['result'][0]
  master_branch_json['planName'] = 'master'
  json_with_build_result_information << master_branch_json
  master_branch_json['plan']['branches']['branch'].each { |branch| json_with_build_result_information << branch['latestResult'] }
  json_with_build_result_information.map { |json| extract_build_outcome_from_json json }
end

all_branch_results(json).each { |result| print result.to_s + "\n" }
