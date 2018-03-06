FROM openjdk:8
MAINTAINER Ondrej Kucera <ondra.kuca@gmail.com>

# Scala related variables.
ARG SCALA_VERSION=2.12.4
ARG SCALA_BINARY_ARCHIVE_NAME=scala-${SCALA_VERSION}
ARG SCALA_BINARY_DOWNLOAD_URL=https://downloads.lightbend.com/scala/${SCALA_VERSION}/${SCALA_BINARY_ARCHIVE_NAME}.tgz

# SBT related variables.
ARG SBT_VERSION=1.1.0
ARG SBT_BINARY_ARCHIVE_NAME=sbt-${SBT_VERSION}
ARG SBT_BINARY_DOWNLOAD_URL=https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/${SBT_BINARY_ARCHIVE_NAME}.tgz

# Configure env variables for Scala, SBT .
# Also configure PATH env variable to include binary folders of Java, Scala, SBT.
ENV SCALA_HOME  /usr/local/scala
ENV SBT_HOME    /usr/local/sbt
ENV PATH        $JAVA_HOME/bin:$SCALA_HOME/bin:$SBT_HOME/bin:$PATH

RUN apt-get -yqq update && \
    apt-get install -yqq vim less && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    rm -rf /tmp/* && \
    wget -qO - ${SCALA_BINARY_DOWNLOAD_URL} | tar -xz -C /usr/local/ && \
    wget -qO - ${SBT_BINARY_DOWNLOAD_URL} | tar -xz -C /usr/local/ && \
    cd /usr/local/ && \
    ln -s ${SCALA_BINARY_ARCHIVE_NAME} scala
    
COPY init.d /opt/init.d

COPY target/scala-2.12/news-seeder-client*.jar /opt/server.jar

EXPOSE 80

ENTRYPOINT /opt/init.d/start.sh

CMD ["/bin/bash"]
