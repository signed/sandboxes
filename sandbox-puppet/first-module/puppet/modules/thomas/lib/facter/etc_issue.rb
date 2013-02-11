Facter.add("etc_issue") do
  setcode do
    Facter::Util::Resolution.exec('/bin/cat /etc/issue').chomp
  end
end