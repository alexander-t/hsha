FROM resin/rpi-raspbian:jessie-20160831
MAINTAINER Alexander Tarlinder <a@tarlinder.net>

RUN apt-get update && \
    apt-get -qy install \
	ca-certificates \ 
	oracle-java8-jdk 

RUN mkdir -p /opt/hub
COPY hub.jar /opt/hub/

WORKDIR /opt/hub

EXPOSE 8090
ENTRYPOINT ["java", "-Dserver.port=8090", "-jar", "hub.jar"]

