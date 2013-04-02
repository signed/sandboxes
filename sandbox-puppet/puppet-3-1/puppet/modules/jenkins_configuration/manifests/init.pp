class jenkins_configuration{
    include jenkins_configuration::locations

    file { "override-configuration" :
        ensure => file,
        path => "$jenkins_configuration::locations::main",
        content => template('jenkins_configuration/config.xml.erb'),
        notify => Service['jenkins'],
    }
}