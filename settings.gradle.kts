rootProject.name = "Ruslo"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

include("library")
include("library:yml-expander")
include("microservice")
include("starter")
include("bm-common")
include("bm-domain")
include("bm-domain:bm-converter")
include("bm-domain:bm-user")
include("bm-domain:bm-user:bm-user-db")
include("bm-domain:bm-user:bm-user-usecase")
include("bm-domain:bm-server")
include("bm-domain:bm-server:bm-server-db")
include("bm-domain:bm-server:bm-server-usecase")
include("bm-domain:bm-channel")
include("bm-domain:bm-channel:bm-channel-db")
include("bm-domain:bm-channel:bm-channel-usecase")
include("bm-gateway")
include("bm-gateway:bm-dto")
include("bm-gateway:bm-security")
include("bm-gateway:bm-api")
include("bm-gateway:bm-websocket")
include("bm-application")
include("bm-domain:bm-server:bm-server-api-dto")
include("bm-domain:bm-channel:bm-channel-api-dto")
include("bm-gateway:bm-mapper")
include("bm-gateway:bm-access-control")