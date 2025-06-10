plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    implementation(libs.springDataCommons)
    implementation(libs.databasePostgresR2dbc)
    implementation(libs.jacksonDatabind)
}