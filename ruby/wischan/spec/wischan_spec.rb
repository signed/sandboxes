require 'spec_helper'

describe Wischan do

  it "should greet the person confronted with" do
    greeting = Wischan.greet('developer')
    greeting.should == 'Hello developer'
  end

end
