FROM eclipse-temurin:23
ADD build/libs/events-0.0.1-SNAPSHOT.jar events-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "events-service.jar"]
