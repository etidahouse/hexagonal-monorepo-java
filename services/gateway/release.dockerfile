FROM openjdk:17

COPY ./build/gateway.jar /opt/gateway.jar

CMD [ "java", "-jar", "/opt/gateway.jar"]
