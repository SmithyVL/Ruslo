FROM eclipse-temurin:25
LABEL org.opencontainers.image.authors="Smithy"
COPY ./build/libs/channel-service-0.0.1-SNAPSHOT.jar channel-service.jar
ENTRYPOINT ["java","-jar","/channel-service.jar"]