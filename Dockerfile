FROM openjdk:17-jdk

WORKDIR /zip-go
COPY build/libs/*SNAPSHOT.jar /zip-go/house.jar

ENTRYPOINT ["java", "-jar", "/zip-go/house.jar"]