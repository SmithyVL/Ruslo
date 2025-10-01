plugins {
    id("conventional-plugin")
}

dependencies {
    api(project(":library:dto:user-dto"))

    implementation(project(":library:util:yml-expander"))
    implementation(libs.springBootStarter)
    implementation(libs.springBootStarterWebflux)

    testImplementation(libs.springBootStarterTest)
}