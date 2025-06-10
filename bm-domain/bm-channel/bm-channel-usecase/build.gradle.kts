plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появились классы сущностей.
    api(project(":bm-domain:bm-channel:bm-channel-db"))

    implementation(libs.kotlinxCoroutinesReactor)
}