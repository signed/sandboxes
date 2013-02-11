node default {
  notify{
    "hello puppet":
  }

  notify{
    "User passed from Vagrantfile: '$vagrant_user'":
  }
}