plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    api(project(":bm-domain:bm-converter"))
    api(project(":bm-domain:bm-channel:bm-channel-db"))
    api(project(":bm-domain:bm-channel:bm-channel-api-dto"))

    implementation(project(":bm-common"))
    implementation(libs.kotlinxCoroutinesReactor)
}