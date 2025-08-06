FROM eclipse-temurin:21-jdk
LABEL maintainer="jrcsegador@mymail.mapua.edu.ph"
WORKDIR /opt/app
EXPOSE 8080
COPY target/komandosb-0.0.1-SNAPSHOT.jar /opt/app/komandosb.jar
ENTRYPOINT ["java", "-jar", "komandosb.jar"]