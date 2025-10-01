plugins {
    id("conventional-plugin")
}

dependencies {
    runtimeOnly(libs.kotlinReflect)

    implementation(libs.springBootStarterWebflux)
    implementation(libs.springBootStarterIntegration)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.springDocWebflux)
    implementation(project(":library:util:exceptions-core"))
    implementation(project(":library:starter:technology:reactive-security-starter"))
    implementation(project(":library:starter:technology:reactive-websocket-starter"))
    implementation(project(":library:starter:client:user-service-client-starter"))
    implementation(project(":library:starter:client:server-service-client-starter"))
    implementation(project(":library:starter:client:channel-service-client-starter"))

    testImplementation(libs.springBootStarterTest)
}

// Включаем эту задачу для подмодуля, который является "Spring Boot" приложением.
tasks.bootJar {
    enabled = true
}