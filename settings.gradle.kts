rootProject.name = "Ruslo"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

include("library")
include("library:starter")
include("library:starter:client")
include("library:starter:client:channel-service-client-starter")
include("library:starter:client:server-service-client-starter")
include("library:starter:client:user-service-client-starter")
include("library:starter:technology")
include("library:starter:technology:reactive-security-starter")
include("library:starter:technology:reactive-websocket-starter")
include("library:dto")
include("library:dto:channel-dto")
include("library:dto:member-dto")
include("library:dto:message-dto")
include("library:dto:role-dto")
include("library:dto:server-dto")
include("library:dto:user-dto")
include("library:dto:websocket-dto")
include("library:util")
include("library:util:common-database")
include("library:util:date-format")
include("library:util:exceptions-core")
include("library:util:yml-expander")
include("microservice")
include("microservice:channel-service")
include("microservice:gateway-service")
include("microservice:server-service")
include("microservice:user-service")