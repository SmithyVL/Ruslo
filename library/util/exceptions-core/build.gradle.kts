plugins {
    id("conventional-plugin")
}

dependencies {
    implementation(libs.kotlinxCoroutinesReactor)
    implementation(libs.springContext)
    implementation(libs.springWebflux)
}