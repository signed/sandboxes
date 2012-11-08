    file {'testfolder':
      path    => '/home/signed/tmp/applications/',
      ensure  => directory,
    }
    
    file{'link to latest applications':
      path   => '/home/signed/tmp/current',
      ensure => link,
      target => '/home/signed/tmp/applications',
    }

    file {'testfile':
      path    => '/home/signed/tmp/testfile',
      ensure  => present,
      mode    => 0640,
      content => "I'm a test file.",
    }