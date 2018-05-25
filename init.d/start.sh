#!/usr/bin/env bash

#if [ -z "$CASSANDRA_HOSTS" ]; then
#  echo "CASSANDRA_HOSTS env var must be set" >&2
#fi

exec java -jar /opt/server.jar

