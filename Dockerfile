FROM ondrejkucera/news-debian-base-docker
MAINTAINER Ondrej Kucera <ondra.kuca@gmail.com>


COPY init.d /opt/init.d
COPY target/scala-2.12/news-seeder-client_2.12*.jar /opt/server.jar

EXPOSE 8080

ENTRYPOINT /opt/init.d/start.sh

CMD ["/bin/bash"]
