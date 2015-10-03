# :first_in sets how long it takes before the job is first run. In this case, it is run immediately

def plan_status
  {
      :bambooNotReachable => [true, false].sample,
      :random_number => rand(1000)
  }
end

SCHEDULER.every '1s', allow_overlapping: false do |job|
  send_event('bamboo', plan_status())
end