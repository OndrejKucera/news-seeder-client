FROM ondrejkucera/news-base-docker-debian
MAINTAINER Ondrej Kucera <ondra.kuca@gmail.com>


COPY init.d /opt/init.d
COPY target/scala-2.11/news-seeder-client-assembly*.jar /opt/server.jar

EXPOSE 8181

ENTRYPOINT /opt/init.d/start.sh

CMD ["/bin/bash"]
