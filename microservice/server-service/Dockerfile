FROM eclipse-temurin:25
LABEL org.opencontainers.image.authors="Smithy"
COPY ./build/libs/server-service-0.0.1-SNAPSHOT.jar server-service.jar
ENTRYPOINT ["java","-jar","/server-service.jar"]