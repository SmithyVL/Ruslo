plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterIntegration)

    testImplementation(libs.springBootStarterTest)
}