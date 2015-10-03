# :first_in sets how long it takes before the job is first run. In this case, it is run immediately
SCHEDULER.every '1s' do |job|
  send_event('bamboo', { random_number: rand(1000) })
end