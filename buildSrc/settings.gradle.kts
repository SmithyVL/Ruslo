dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    // Использование репозиториев в "buildSrc".
    repositories {
        mavenCentral()
    }

    // Использование каталога версий в "buildSrc".
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "buildSrc"