import java.lang.Runtime.getRuntime

// В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов, который для начала нужно
// создать.
val mockitoAgent = configurations.create("mockitoAgent")

group = "ru.blimfy"

plugins {
    alias(libs.plugins.springBoot)
    alias(libs.plugins.dependencyManagement)
    alias(libs.plugins.kotlinJvm)
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
    runtimeOnly(libs.jwtJackson)
    runtimeOnly(libs.jacksonJsr310)

    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterDataR2dbc)
    implementation(libs.springBootStarterSecurity)
    implementation(libs.springBootStarterIntegration)
    implementation(libs.springDocWebflux)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.jwtImpl)

    testImplementation(libs.springBooStarterTest)

    // В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов, в том числе и в
    // зависимостях.
    testImplementation(libs.mockito)
    mockitoAgent(libs.mockito) { isTransitive = false }
}

tasks {
    compileJava {
        // Запуск компилятора в отдельном процессе.
        options.isFork = true
    }

    test {
        // Новые версии gradle требуют явно указывать платформу для выполнения тестов.
        useJUnitPlatform()

        jvmArgs(
            // В более старших версиях Java возникает предупреждение: "Sharing is only supported for bootloader
            // classes because bootstrap classpath has been appended".
            "-Xshare:off",

            // В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов.
            "-javaagent:${mockitoAgent.asPath}"
        )
    }
}

tasks.withType<Test>().configureEach {
    // Отключение отчётов о тестировании.
    reports.html.required = false
    reports.junitXml.required = false

    // Создание новой ВМ после выполнения очередных 100 тестов. Уменьшать значение не рекомендуется. Оно должно быть
    // достаточно большим, потому что создание дополнительных ВМ довольно "трудоёмкое" занятие.
    forkEvery = 100

    // Параллельное выполнение тестов.
    maxParallelForks = (getRuntime().availableProcessors() / 2).coerceAtLeast(1)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(23)
}