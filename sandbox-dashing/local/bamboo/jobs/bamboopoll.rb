# :first_in sets how long it takes before the job is first run. In this case, it is run immediately

def failed_branch(branch_name, failed_because)
  {
      :name => branch_name,
      :failed_because => failed_because,
      :link => 'http://localhost:3030/sample'
  }
end

def plan_status
  {
      :bamboo_not_reachable => [false, false, false, false, false].sample,
      :failed_plans => [
          failed_branch('bugfix/gone-wrong', 'merge conflict'),
          failed_branch('feature/awesome', 'tests failing')
      ],
      :random_number => rand(1000) #just for debugging reasons, can be removed
  }
end

SCHEDULER.every '1m', allow_overlapping: false do |job|
  send_event('bamboo', plan_status())
end