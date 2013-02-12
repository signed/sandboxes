#!/bin/bash
if [ ! -f puppetlabs-release-precise.deb ];
then
    wget http://apt.puppetlabs.com/puppetlabs-release-precise.deb
    sudo dpkg -i puppetlabs-release-precise.deb
    sudo apt-get update
fi
