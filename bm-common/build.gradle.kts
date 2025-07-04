plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    implementation(libs.springDataCommons)
    implementation(libs.databasePostgresR2dbc)
    implementation(libs.jacksonDatabind)
}