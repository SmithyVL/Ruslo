plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    runtimeOnly(libs.springBootStarterValidation)

    implementation(project(":bm-gateway:bm-dto"))
    implementation(project(":bm-gateway:bm-mapper"))
    implementation(project(":bm-gateway:bm-security"))
    implementation(project(":bm-gateway:bm-websocket"))
    implementation(project(":bm-gateway:bm-access-control"))
    implementation(project(":bm-domain:bm-user:bm-user-usecase"))
    implementation(project(":bm-domain:bm-server:bm-server-usecase"))
    implementation(project(":bm-domain:bm-channel:bm-channel-usecase"))
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.springDocWebflux)
}