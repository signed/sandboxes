node default {
  notify{
    "hello puppet":
  }

  notify{
    "User passed from Vagrantfile: '$vagrant_user'":
  }

  notify{ "read-the-function":
    message =>the_number_is(42)
  }

  include thomas
  include thomas::custom_facts::etc_issue


}