import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")

    // Превращает модуль в Spring Boot приложение с указанием версии.
    alias(libs.plugins.springBoot)

    // Предоставляет удобный доступ к зависимостям, входящим в текущую выбранную версию Spring Boot.
    alias(libs.plugins.springDependencyManagement)

    // Предоставляет возможность взаимодействия Kotlin с Java.
    alias(libs.plugins.kotlinJvm)

    // Делает все классы Kotlin открытыми (open).
    alias(libs.plugins.kotlinSpring)
}

// Пропуск этой задачи нужен для выполнения корректной сборки с тестами с использованием всех преимуществ Spring Boot
// плагина.
tasks.named<BootJar>("bootJar") {
    enabled = false
}

dependencies {
    // "Отправляем" зависимость "наружу" для того, чтобы появился интерфейс - "ReactiveUserDetailsService", для
    // создания реализации.
    api(libs.springBootStarterSecurity)

    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.jwtJackson)

    implementation(libs.springBootStarter)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.jwtImpl)

    testImplementation(libs.springBootStarterTest)
}