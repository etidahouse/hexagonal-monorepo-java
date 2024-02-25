FROM openjdk:17

COPY ./build/mongodb-storage.jar /opt/mongodb-storage.jar

CMD [ "java", "-jar", "/opt/mongodb-storage.jar"]
