require 'page'

describe Page do

  [2012, 2015, 1748].each do |year|
    it "should return the year part as string #{year}" do
      page = Page.new(Time.new(year, 10, 1))
      page.year.should == "#{year}"
    end
  end
end