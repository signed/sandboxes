require "wischan/version"

module Wischan
  def self.greet(recipient)
    "Hello #{recipient}"
  end

  def self.last_in_year(year)
    Page.new("")

    date == @model.collection.all.select do |page|
      page.year == year
    end.sort {|x, y| x.date <=> y.date}.last.date
  end
end
