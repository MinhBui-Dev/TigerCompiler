# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/jammy64"
  config.vm.box_check_update = false
  config.vm.hostname = "mbui"
  config.vm.define "Compiler-vm"
  # Remember to install vagrant-vbguest plugin with the command:
  #
  #   vagrant plugin install vagrant-vbguest
  #
  config.vm.provider "virtualbox" do |vb|
    vb.gui = true
    vb.cpus = 2
    vb.name = "Compiler"
  end
  config.vm.provision "file", source: "spimfixes.patch", destination: "/home/vagrant/spimfixes.patch"
  config.vm.provision "shell", inline: <<-SHELL
    apt-get update
    apt-get install --assume-yes \
      build-essential \
      gdb \
      ant \
      cmake \
      git \
      maven \
      openjdk-17-jdk \
      pkg-config \
      unzip \
      uuid-dev
    #
    # Retrieve antlr
    ANTLR_BUILD_DIR=$(mktemp -d /tmp/antlr.XXX)
    cd $ANTLR_BUILD_DIR
    wget http://www.antlr.org/download/antlr-4.11.1-complete.jar
    wget http://www.antlr.org/download/antlr4-cpp-runtime-4.11.1-source.zip
    wget http://www.antlr.org/download/antlr-runtime-4.11.1.jar
    #
    # Build c++ Antlr lib
    mkdir tmp
    cd tmp
    unzip ../antlr4-cpp-runtime-4.11.1-source.zip
    mkdir build && cd build
    cmake ..
    make -j 2
    DESTDIR=/ make install
    cd ../..
    rm -rf tmp
    #
    # Install Java Antlr lib and set Classpath
    mv antlr-4.11.1-complete.jar /usr/local/lib/
    mv antlr-runtime-4.11.1.jar /usr/local/lib/
    echo 'export CLASSPATH=".:/usr/local/lib/antlr-4.11.1-complete.jar:$CLASSPATH"' >> /etc/profile.d/setClassPath.sh
    #
    # Create antlr link
    echo '#!/bin/sh -ue' > /usr/bin/antlr
    echo 'java -jar /usr/local/lib/antlr-4.11.1-complete.jar $@' >> /usr/bin/antlr
    chmod 755 /usr/bin/antlr
    #
    # Build spim with keepstats
    apt-get install --assume-yes bison flex
    git clone https://github.com/rudyjantz/spim-keepstats
    cd spim-keepstats
    patch -p1 < /home/vagrant/spimfixes.patch
    cd spim
    make && make install
    apt-get remove --purge --assume-yes bison flex
    #
    #Clean up
    cd /tmp
    rm -rf $ANTLR_BUILD_DIR
    ldconfig
    # Block Internet access.
    # echo '#!/bin/sh -ue' > /etc/rc.local
    # echo 'iptables -A INPUT --src $(hostname -I | xargs)/24 -j ACCEPT' >> /etc/rc.local
    # echo 'iptables -A INPUT -j REJECT' >> /etc/rc.local
    # chmod +x /etc/rc.local
  SHELL
end
