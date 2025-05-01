import java.lang.Runtime.getRuntime

// В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов, который для начала нужно
// создать.
val mockitoAgent = configurations.create("mockitoAgent")

// Общие плагины для подмодулей.
plugins {
    // Превращает модуль в Spring Boot приложение с указанием версии.
    id("org.springframework.boot")

    // Предоставляет удобный доступ к зависимостям, входящим в текущую выбранную версию Spring Boot.
    id("io.spring.dependency-management")

    // Плагин компилятора Kotlin для JVM.
    kotlin("jvm")

    // Плагин Kotlin для того, чтобы все классы, используемые Spring стали открытыми (open).
    kotlin("plugin.spring")
}

// Общая информация о подмодуле.
group = "ru.blimfy"
version = "0.0.1-SNAPSHOT"

// Общие зависимости для подмодулей.
dependencies {
    val mockito = "org.mockito:mockito-core"
    testImplementation(mockito)

    // В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов, в том числе и в
    // зависимостях.
    mockitoAgent(mockito) { isTransitive = false }
}

tasks {
    // Выключаем эту задачу по дефолту, потому что основное количество подмодулей не являются приложениями.
    bootJar {
        enabled = false
    }

    // Общая конфигурация задачи компиляции для подмодулей.
    compileJava {
        // Запуск компилятора в отдельном процессе.
        options.isFork = true
    }

    // Общая конфигурация задачи тестов для подмодулей.
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

        // Параллельное выполнение тестов.
        maxParallelForks = (getRuntime().availableProcessors() / 2).coerceAtLeast(1)

        // Создание новой ВМ после выполнения очередных 100 тестов. Уменьшать значение не рекомендуется. Оно должно быть
        // достаточно большим, потому что создание дополнительных ВМ довольно "трудоёмкое" занятие.
        forkEvery = 100

        // Отключение отчётов о тестировании.
        reports.html.required = false
        reports.junitXml.required = false
    }
}

// Конфигурация версии JVM для Kotlin.
kotlin {
    jvmToolchain(23)
}