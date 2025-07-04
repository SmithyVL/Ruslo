plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    implementation(project(":bm-common"))
    implementation(project(":bm-domain:bm-user:bm-user-usecase"))
    implementation(project(":bm-domain:bm-server:bm-server-usecase"))
    implementation(project(":bm-domain:bm-channel:bm-channel-usecase"))
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.springDocWebflux)
}