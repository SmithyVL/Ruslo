FROM eclipse-temurin:25
LABEL org.opencontainers.image.authors="Smithy"
COPY ./build/libs/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java","-jar","/gateway-service.jar"]