import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    runtimeOnly(libs.springBootStarterActuator)
    runtimeOnly(libs.springBootStarterDataR2dbc)

    implementation(project(":bm-gateway"))
    implementation(libs.springBootStarterWebflux)

    testImplementation(libs.springBootStarterTest)
    testImplementation(libs.testcontainersPostgres)
}

// Включаем эту задачу для подмодуля, который является приложением.
tasks.named<BootJar>("bootJar") {
    enabled = true
}