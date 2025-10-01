plugins {
    id("conventional-plugin")
}

dependencies {
    runtimeOnly(libs.kotlinReflect)

    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterIntegration)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.jacksonDatabind)

    testRuntimeOnly(libs.springBootStarterWebflux)
    testImplementation(libs.springBootStarterTest)
}