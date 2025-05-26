plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    api(project(":bm-domain:bm-channel:bm-channel-db"))
    // "Отправляем" зависимость "наружу" для того, чтобы появились классы сущностей.

    implementation(project(":bm-common"))
    implementation(libs.kotlinxCoroutinesReactor)
}