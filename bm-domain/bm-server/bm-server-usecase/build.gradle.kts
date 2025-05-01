plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появились классы сущностей.
    api(project(":bm-domain:bm-server:bm-server-db"))

    implementation(project(":bm-common"))
    implementation(libs.kotlinxCoroutinesReactor)
}