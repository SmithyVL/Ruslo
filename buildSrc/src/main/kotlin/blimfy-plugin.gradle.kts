import java.lang.Runtime.getRuntime

group = "ru.blimfy"
version = "0.0.1-SNAPSHOT"

plugins {
    id("java")
}

repositories {
    mavenCentral()
}

// В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов, который для начала нужно
// создать.
val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    val mockito = "org.mockito:mockito-core"
    testImplementation(mockito)

    // В более старших версиях Mockito требуется явно указывать "javaagent" для выполнения тестов, в том числе и в
    // зависимостях.
    mockitoAgent(mockito) { isTransitive = false }
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