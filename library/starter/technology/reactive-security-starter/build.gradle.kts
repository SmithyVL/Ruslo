plugins {
    id("conventional-plugin")
}

dependencies {
    api(libs.springBootStarterSecurity)

    runtimeOnly(libs.kotlinReflect)
    runtimeOnly(libs.jwtJackson)

    implementation(project(":library:util:yml-expander"))
    implementation(project(":library:util:exceptions-core"))
    implementation(libs.springBootStarter)
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.jwtImpl)

    testRuntimeOnly(libs.springBootStarterWebflux)
    testImplementation(libs.springBootStarterTest)
}