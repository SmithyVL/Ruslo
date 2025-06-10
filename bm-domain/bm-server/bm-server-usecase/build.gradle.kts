plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появился интерфейс "CoroutineCrudRepository".
    api(project(":bm-domain:bm-server:bm-server-db"))

    implementation(libs.kotlinxCoroutinesReactor)
}