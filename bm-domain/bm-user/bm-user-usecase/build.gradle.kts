plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    api(project(":bm-domain:bm-converter"))
    // "Отправляем" зависимость "наружу" для того, чтобы появились классы сущностей.
    api(project(":bm-domain:bm-user:bm-user-db"))

    implementation(libs.kotlinxCoroutinesReactor)
}