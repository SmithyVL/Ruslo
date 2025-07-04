plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    api(project(":bm-common"))
    api(project(":bm-domain:bm-server:bm-server-usecase"))
    api(project(":bm-domain:bm-user:bm-user-usecase"))

    runtimeOnly(libs.kotlinReflect)

    implementation(project(":bm-gateway:bm-dto"))
    implementation(project(":bm-gateway:bm-mapper"))
    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterIntegration)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.jwtImpl)

    testImplementation(libs.springBootStarterTest)
}