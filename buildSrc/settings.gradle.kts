dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    // Использование репозиториев в "buildSrc".
    repositories {
        mavenLocal()
        mavenCentral()
    }

    // Использование каталога версий в "buildSrc".
    versionCatalogs {
        register("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "buildSrc"