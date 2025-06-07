plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    implementation(libs.springDataCommons)

    // Использование "Json".
    implementation(libs.databasePostgresR2dbc)

    // Использование "ObjectMapper".
    implementation(libs.jacksonDatabind)
}