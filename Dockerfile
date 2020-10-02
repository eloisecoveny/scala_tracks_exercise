FROM centos:latest
USER root

RUN yum -y install openssl jq redis yum-utils
RUN yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

ENV SBT_VERSION 1.3.4

RUN redis-server
RUN curl -v -0 https://bintray.com/sbt/rpm/download_file?file_path=sbt-$SBT_VERSION.rpm -L --output sbt-$SBT_VERSION.rpm
RUN yum -y localinstall sbt-$SBT_VERSION.rpm
RUN sbt clean test