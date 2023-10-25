FROM mongo:7.0.2

RUN apt update -y && apt upgrade -y

RUN apt install openjdk-17-jdk -y

RUN apt install maven -y
