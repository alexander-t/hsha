FROM resin/rpi-raspbian:jessie-20160831
MAINTAINER Alexander Tarlinder <a@tarlinder.net>

RUN apt-get update && \
    apt-get -qy install \
	ca-certificates \ 
	oracle-java8-jdk 

RUN mkdir -p /opt/web
COPY web.jar /opt/web/

WORKDIR /opt/hub

EXPOSE 80
ENTRYPOINT ["java", "-jar", "web.jar"]

