plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появился интерфейс - "CoroutineCrudRepository", для
    // использования его методов.
    api(libs.springBootStarterDataR2dbc)

    runtimeOnly(libs.databasePostgres)
    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.hikari)

    implementation(project(":bm-common"))
    implementation(libs.springBootStarter)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.databasePostgresR2dbc)
    implementation(libs.jacksonDatabind)
    implementation(libs.liquibaseCore)

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
}