plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // Исправление ошибки уровня "INFO": "Failed to set up a Bean Validation provider:
    // jakarta.validation.NoProviderFoundException".
    runtimeOnly(libs.springBootStarterValidation)
    runtimeOnly(libs.jacksonJsr310)

    implementation(project(":bm-common"))
    implementation(project(":bm-integration:bm-security"))
    implementation(project(":bm-integration:bm-websocket"))
    implementation(project(":bm-domain:bm-user:bm-user-usecase"))
    implementation(project(":bm-domain:bm-server:bm-server-usecase"))
    implementation(project(":bm-domain:bm-direct:bm-direct-usecase"))
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.springDocWebflux)
}