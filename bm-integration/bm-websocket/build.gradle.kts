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
    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterIntegration)

    testImplementation(libs.springBootStarterTest)
}