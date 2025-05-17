plugins {
    // Включает логику общую для всех модулей.
    id("blimfy-plugin")
}

dependencies {
    implementation(libs.springCore)
    implementation(libs.springBeans)
    implementation(libs.springDataCommons)
}