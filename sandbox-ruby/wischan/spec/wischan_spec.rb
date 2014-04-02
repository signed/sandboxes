require 'spec_helper'

describe Wischan do
  it "should return data for a named element" do
    greeting = Wischan.greet('developer')
    greeting.should_not be_nil
    greeting.should == 'Hello developer'
  end
end
