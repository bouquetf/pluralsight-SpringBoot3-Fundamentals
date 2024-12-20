FROM eclipse-temurin:23
ADD build/libs/registration-0.0.1-SNAPSHOT.jar registration-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "registration-service.jar"]
