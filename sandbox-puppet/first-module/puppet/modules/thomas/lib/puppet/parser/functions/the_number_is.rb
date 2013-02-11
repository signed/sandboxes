module Puppet::Parser::Functions
  newfunction(:the_number_is, :type =>:rvalue) do |args|
    number = args[0]
    "the number is '"+number+"'"
  end
end