class Page

  def initialize(date)
    @date = date
  end

  def year()
    @date.strftime("%Y")
  end

end