plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    api(libs.springBootStarterDataR2dbc)

    runtimeOnly(libs.databasePostgres)
    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.hikari)

    implementation(project(":bm-common"))
    implementation(project(":bm-domain:bm-converter"))
    implementation(libs.springBootStarter)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.databasePostgresR2dbc)
    implementation(libs.jacksonDatabind)
    implementation(libs.liquibaseCore)

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
}