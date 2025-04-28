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

dependencies {
    runtimeOnly(libs.springBootStarterActuator)
    // Исправление ошибки уровня "INFO": "Failed to set up a Bean Validation provider:
    // jakarta.validation.NoProviderFoundException".
    runtimeOnly(libs.springBootStarterValidation)
    runtimeOnly(libs.kotlinReactorExtension)
    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.databasePostgresR2dbc)
    runtimeOnly(libs.jacksonJsr310)
    runtimeOnly(libs.databasePostgres)
    runtimeOnly(libs.liquibaseCore)

    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterDataR2dbc)
    implementation(libs.springDocWebflux)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(project(":bm-integration:bm-security"))
    implementation(project(":bm-integration:bm-websocket"))

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
}