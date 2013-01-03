class java {
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

$user_name = "signed"

group{ $user_name:
     ensure => present,
}

user { $user_name:
        ensure => present,
        #ensure => absent,
        gid => "signed",
        shell => "/bin/bash",
        #password => '$6$8/7I8a1F$c6AzeCEUyI77dRUca9Z8WixEt2D6lbF7Or1NbYKB8Dm.VfC9fC1FyiQdvFOq9Ddk7ao54xRcy.APYCjc.fWud.',
        managehome => true,
}


group{ 'nigel':
  ensure => 'present',
}

user { 'nigel':
  ensure           => 'present',
  comment          => 'nigel,,,',
  gid              => 'nigel',
  home             => '/home/nigel',
  password => '$1$lU8491Lf$07pmQGDJNZKuRMc/VuRGG/',
  password_max_age => '99999',
  password_min_age => '0',
  shell            => '/bin/bash',
}


file { "/opt/tmp" :
    ensure => directory,
}

include java
include maven
    
    