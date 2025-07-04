plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появился интерфейс - "ReactiveUserDetailsService", для
    // создания реализации.
    api(libs.springBootStarterSecurity)
    api(project(":bm-common"))

    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.jwtJackson)

    implementation(project(":bm-domain:bm-user:bm-user-usecase"))
    implementation(libs.springBootStarter)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.jwtImpl)

    testImplementation(libs.springBootStarterTest)
    testRuntimeOnly(libs.springBootStarterWebflux)
}