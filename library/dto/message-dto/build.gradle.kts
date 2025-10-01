plugins {
    id("conventional-plugin")
}

dependencies {
    api(libs.kotlinxCoroutinesReactor)

    implementation(project(":library:util:date-format"))
    implementation(project(":library:dto:user-dto"))
    implementation(libs.jakartaValidation)
}