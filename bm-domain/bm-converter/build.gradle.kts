plugins {
    // Включает логику общую для всех модулей.
    id("conventional-plugin")
}

dependencies {
    // Модели, используемые в сущностях, требующие создания для них конвертеров.
    api(project(":bm-common"))

    // Интерфейсы конвертеров
    implementation(libs.springDataCommons)

    // "Json" класс.
    implementation(libs.databasePostgresR2dbc)

    // "ObjectMapper" класс.
    implementation(libs.jacksonDatabind)
}