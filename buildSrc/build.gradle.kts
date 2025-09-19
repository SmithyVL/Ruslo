plugins {
    // Даёт писать сценарии сборки Gradle с использованием Kotlin вместо традиционного синтаксиса Groovy.
    `kotlin-dsl`
}

dependencies {
    // Для появления нужных плагинов/зависимостей в conventional плагине нужно объявить их как зависимости в этом файле.
    implementation(plugin(libs.plugins.springBoot))
    implementation(plugin(libs.plugins.kotlinJvm))
    implementation(plugin(libs.plugins.kotlinSpring))
}

/**
 * Возвращает преобразованный псевдоним [plugin] gradle из "Version catalog" в допустимую строку зависимости для
 * "buildSrc".
 */
fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }