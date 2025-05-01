plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // Исправление ошибки уровня "INFO": "Failed to set up a Bean Validation provider:
    // jakarta.validation.NoProviderFoundException".
    runtimeOnly(libs.springBootStarterValidation)
    runtimeOnly(libs.springBootStarterActuator)
    runtimeOnly(libs.jacksonJsr310)

    api(project(":bm-common"))
    implementation(project(":bm-integration:bm-security"))
    implementation(project(":bm-integration:bm-websocket"))
    api(project(":bm-domain:bm-user:bm-user-usecase"))
    api(project(":bm-domain:bm-user:bm-user-db"))
    implementation(project(":bm-domain:bm-server:bm-server-usecase"))
    implementation(project(":bm-domain:bm-direct:bm-direct-usecase"))
    implementation(libs.springBootStarterWebflux)
    implementation(libs.springDocWebflux)
    implementation(libs.kotlinxCoroutinesReactor)
}