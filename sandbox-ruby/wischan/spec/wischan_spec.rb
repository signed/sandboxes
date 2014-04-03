require 'spec_helper'

describe Wischan do

  it "should greet the person confronted with" do
    greeting = Wischan.greet('developer')
    greeting.should == 'Hello developer'
  end

  it "should return a single year" do
    year = Wischan.last_in_year("2012")
    year.should == "2012"
  end

end
