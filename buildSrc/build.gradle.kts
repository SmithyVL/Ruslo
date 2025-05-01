plugins {
    // Даёт писать сценарии сборки Gradle с использованием Kotlin вместо традиционного синтаксиса Groovy.
    `kotlin-dsl`
}

dependencies {
    // Для появления нужных плагинов в conventional плагине нужно объявить их как зависимости в этом файле.
    implementation(plugin(libs.plugins.springBoot))
    implementation(plugin(libs.plugins.kotlinJvm))
    implementation(plugin(libs.plugins.kotlinSpring))
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }