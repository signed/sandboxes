Puppet::Type.newtype(:empire) do
  @doc = "Create a new empire."
  newproperty(:emperor) do
    desc "The emperor of the empire."
  end
end