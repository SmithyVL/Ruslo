plugins {
    id("conventional-plugin")
}

dependencies {
    api(project(":library:dto:channel-dto"))
    api(project(":library:dto:message-dto"))

    implementation(project(":library:util:yml-expander"))
    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWebflux)
    implementation(libs.kotlinxCoroutinesReactor)

    testImplementation(libs.springBootStarterTest)
}