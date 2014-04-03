class Page

  def self.created_at(yearMonthDay)
    Page.new(Time.new(*yearMonthDay))
  end

  def initialize(date)
    @date = date
  end

  def year()
    @date.strftime("%Y")
  end

  def date
    @date
  end

  def last_in_year(pages)
    @date == pages.select do |page|
      page.year == self.year
    end.sort { |x, y| x.date <=> y.date }.last.date
  end
end