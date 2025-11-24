FROM eclipse-temurin:25
LABEL org.opencontainers.image.authors="Smithy"
COPY ./build/libs/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java","-jar","/user-service.jar"]