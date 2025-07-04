plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    api(project(":bm-domain:bm-converter"))
    api(project(":bm-domain:bm-server:bm-server-db"))
    api(project(":bm-domain:bm-server:bm-server-api-dto"))

    implementation(libs.kotlinxCoroutinesReactor)
}