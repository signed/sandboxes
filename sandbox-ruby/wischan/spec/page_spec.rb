require 'page'

describe Page do

  [2012, 2015, 1748].each do |year|
    it "should return the year part as string #{year}" do
      page = Page.new(Time.new(year, 10, 1))
      page.year.should == "#{year}"
    end
  end

  first_page_in_2012 = Page.created_at(2012, 3, 5)
  last_page_in_2012 = Page.created_at(2012, 7, 18)

  it "should return true for the last page in the year" do
    pages = [last_page_in_2012, Page.created_at(2012, 7, 5), Page.created_at(2013, 7, 5), first_page_in_2012]
    last_page_in_2012.last_in_year(pages).should == true
  end

  it "should return false if the page is not the last in the year" do
    pages = [last_page_in_2012, Page.created_at(2012, 7, 5), Page.created_at(2013, 7, 5), first_page_in_2012]
    first_page_in_2012.last_in_year(pages).should == false
  end

end