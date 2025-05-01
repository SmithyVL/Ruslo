package ru.blimfy.user.db

import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import ru.blimfy.common.factory.CustomConfigPropertiesReaderFactory

/**
 * Авто-конфигурация для подключения бинов для работы с сущностями пользователя в БД.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration(after = [R2dbcDataAutoConfiguration::class])
@ComponentScan
@PropertySource("classpath:bm-user-db.yml", factory = CustomConfigPropertiesReaderFactory::class)
class UserDatabaseAutoConfiguration {
    @Value("\${bm-user-db.liquibase.changelog}")
    private lateinit var liquibaseChangelog: String

    @Value("\${spring.liquibase.driver-class-name}")
    private lateinit var liquibaseDriver: String

    @Value("\${spring.liquibase.url}")
    private lateinit var liquibaseUrl: String

    @Value("\${spring.liquibase.user}")
    private lateinit var liquibaseUser: String

    @Value("\${spring.liquibase.password}")
    private lateinit var liquibasePassword: String

    /**
     * Создаёт бин Liquibase для выполнения скриптов миграции БД с пользователями.
     */
    @Bean
    fun userLiquibase() =
        SpringLiquibase().apply {
            this.changeLog = liquibaseChangelog
            this.dataSource = DataSourceBuilder.create()
                .driverClassName(liquibaseDriver)
                .url(liquibaseUrl)
                .username(liquibaseUser)
                .password(liquibasePassword)
                .build()
        }
}