node default {
    include apt
    include stdlib
    include jenkins

    package{ "puppet":
        ensure=>latest,
    }

    $plugins = [
      'git',
      'chucknorris',
      'github',
      'greenballs',
      'ruby',
      'rake',
      'ssh',
      'monitoring',
      'favorite',
      'gravatar',
      'jabber',
      'sounds',
      'radiatorviewplugin',
      'vsphere-cloud',
      'warnings',
      'analysis-core',
      'rvm',
      'htmlpublisher',
      'xfpanel'
    ]

    jenkins::plugin::install { $plugins: }
}