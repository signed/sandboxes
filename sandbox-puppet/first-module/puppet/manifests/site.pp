node default {
  notify{
    "hello puppet":
  }

  notify{
    "User passed from Vagrantfile: '$vagrant_user'":
  }

  include thomas
  include thomas::custom_facts::etc_issue
}