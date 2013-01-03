class java {
    include tmp-directory-under-opt
    Class['tmp-directory-under-opt'] -> Class['java']

    $jdk_download_url = "http://download.oracle.com/otn-pub/java/jdk/7u9-b05/jdk-7u9-linux-i586.tar.gz"
    $local_download_url = "/opt/tmp/jdk.tar.gz"
    $local_jdk_path = "/opt/java/jdk1.7.0_09"

    # create needed folders under /opt/
    file { "/opt/java"  :
        ensure => directory,
    } ->

    exec { "download-jdk-7" :
        command => "/usr/bin/wget --no-cookies --header 'Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com' ${jdk_download_url} -O ${local_download_url}",
        creates => $local_download_url,
    } ->

    exec { "unpack-jdk" :
        command => "/bin/tar -xzf ${local_download_url} -C /opt/java",
        creates => $local_jdk_path,
    } ->

    exec { "update-alternatives" :
        command => "/usr/bin/sudo /usr/sbin/update-alternatives --install '/usr/bin/java' 'java' '${local_jdk_path}/bin/java' 1",
        creates => "/usr/bin/java",
    } ->

    file { "add-profile-script":
      path => "/etc/profile.d/set_java_home.sh",
      ensure => present,
      content => "export JAVA_HOME=${local_jdk_path}",
    }
}

class maven{
    include tmp-directory-under-opt
    Class['tmp-directory-under-opt'] -> Class['java']

    $maven_download_url = "http://apache.mirror.clusters.cc/maven/maven-3/3.0.4/binaries/apache-maven-3.0.4-bin.tar.gz"
    $local_download_url = "/opt/tmp/maven.tar.gz"
    $local_maven_path = "/opt/maven/apache-maven-3.0.4"
  
    file { "/opt/maven" :
        ensure => directory,
    } ->

    exec { "download-maven-3-0-4" :
        command => "/usr/bin/wget ${maven_download_url} -O ${local_download_url}",
        creates => $local_download_url,
    } ->

    exec { "unpack-maven" :
        command => "/bin/tar -xzf ${local_download_url} -C /opt/maven",
        creates => $local_maven_path,
    } ->

    file { "put-maven-on-path":
    path => "/etc/profile.d/put-maven-on-path.sh",
    ensure => present,
    content => "PATH=\$PATH:${local_maven_path}/bin",
  }
}

class gem-dependencies{
  package{'make':
      ensure => installed,
  }
}

class patch-ruby-for-puppet{
    include gem-dependencies
    Class['gem-dependencies'] -> Class['patch-ruby-for-puppet']

    exec{ "install-ruby-shadow-gem":
        command => '/opt/vagrant_ruby/bin/gem install ruby-shadow',
        creates => '/opt/vagrant_ruby/lib/ruby/gems/1.8/gems/ruby-shadow-2.1.4',
    }
}


class create-my-user{
    Class['patch-ruby-for-puppet'] -> Class['create-my-user']
    $user_name = "signed"

    group{ $user_name:
         ensure => present,
    }

    user { $user_name:
            ensure => present,
            gid => "signed",
            shell => "/bin/bash",
            password => '$1$1GeHnJqU$AmOhfFhOgsXEV1HurFrW8/', #openssl passwd -1
            managehome => true,
    }
}


class tmp-directory-under-opt{
    file { "/opt/tmp" :
        ensure => directory,
    }
}

include gem-dependencies
include patch-ruby-for-puppet
include create-my-user
include java
include maven




    