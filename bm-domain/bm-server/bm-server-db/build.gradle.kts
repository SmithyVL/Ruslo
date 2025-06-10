plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появился интерфейс "CoroutineCrudRepository".
    api(libs.springBootStarterDataR2dbc)
    api(project(":bm-common"))

    runtimeOnly(libs.databasePostgres)
    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.hikari)

    implementation(libs.springBootStarter)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.databasePostgresR2dbc)
    implementation(libs.jacksonDatabind)
    implementation(libs.liquibaseCore)

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
}