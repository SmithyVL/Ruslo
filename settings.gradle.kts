rootProject.name = "blimfy-backend"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}

include("bm-common")
include("bm-domain")
include("bm-domain:bm-user")
include("bm-domain:bm-user:bm-user-db")
include("bm-domain:bm-user:bm-user-usecase")
include("bm-domain:bm-server")
include("bm-domain:bm-server:bm-server-db")
include("bm-domain:bm-server:bm-server-usecase")
include("bm-domain:bm-direct")
include("bm-domain:bm-direct:bm-direct-db")
include("bm-domain:bm-direct:bm-direct-usecase")
include("bm-integration")
include("bm-integration:bm-security")
include("bm-integration:bm-websocket")
include("bm-gateway")
include("bm-application")