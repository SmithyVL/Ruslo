plugins {
    id("conventional-plugin")
}

dependencies {
    implementation(project(":library:util:date-format"))
    implementation(libs.jakartaValidation)
}