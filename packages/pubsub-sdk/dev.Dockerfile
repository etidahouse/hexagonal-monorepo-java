FROM bitnami/kafka:3.5.2

USER root

RUN apt update -y && apt upgrade -y

RUN apt install openjdk-17-jdk -y

RUN apt install maven -y

RUN mkdir /root/.m2

RUN chmod -R 755 /root/.m2
