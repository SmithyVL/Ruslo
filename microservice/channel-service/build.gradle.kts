plugins {
    id("conventional-plugin")
}

dependencies {
    runtimeOnly(libs.databasePostgres)
    runtimeOnly(libs.kotlinReflect)

    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterDataR2dbc)
    implementation(libs.springBootStarterLiquibase)
    implementation(libs.databasePostgresR2dbc)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.springDocWebflux)
    implementation(project(":library:util:exceptions-core"))
    implementation(project(":library:util:common-database"))
    implementation(project(":library:dto:channel-dto"))
    implementation(project(":library:dto:message-dto"))

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
    testImplementation(libs.testcontainersJunit5)
}

// Включаем эту задачу для подмодуля, который является "Spring Boot" приложением.
tasks.bootJar {
    enabled = true
}