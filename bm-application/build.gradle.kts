import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    // Подключение основной логики микросервиса, которая в будущем будет разделена на отдельные микросервисы.
    implementation(project(":bm-gateway"))

    // Включение авто-конфигурации с помощью аннотаций "SpringBootApplication" и "EnableWebFlux".
    implementation(libs.springBootStarterWebflux)

    // Включение аудита с помощью аннотации "EnableR2dbcAuditing".
    implementation(libs.springBootStarterDataR2dbc)

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
}

// Включаем эту задачу для подмодуля, который является приложением.
tasks.named<BootJar>("bootJar") {
    enabled = true
}