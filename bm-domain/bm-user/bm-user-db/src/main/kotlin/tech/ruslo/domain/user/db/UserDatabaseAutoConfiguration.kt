package tech.ruslo.domain.user.db

import com.fasterxml.jackson.databind.ObjectMapper
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions.of
import org.springframework.data.r2dbc.dialect.PostgresDialect.INSTANCE
import tech.ruslo.common.factory.CustomConfigPropertiesReaderFactory
import tech.ruslo.domain.converter.MessageReferenceReadConverter
import tech.ruslo.domain.converter.MessageReferenceWriteConverter
import tech.ruslo.domain.converter.MessageSnapshotReadConverter
import tech.ruslo.domain.converter.MessageSnapshotWriteConverter

/**
 * Авто-конфигурация для подключения бинов для работы с сущностями пользователя в БД.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@AutoConfiguration(before = [R2dbcDataAutoConfiguration::class])
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

    /**
     * Создаёт бин R2dbcCustomConversions с конфигурацией конвертеров, использующих [objectMapper].
     */
    @Bean
    @ConditionalOnMissingBean
    fun r2dbcCustomConversions(objectMapper: ObjectMapper): R2dbcCustomConversions {
        val converters = mutableListOf(
            MessageReferenceWriteConverter(objectMapper),
            MessageReferenceReadConverter(objectMapper),
            MessageSnapshotReadConverter(objectMapper),
            MessageSnapshotWriteConverter(objectMapper),
        )
        return of(INSTANCE, converters)
    }

    /**
     * Создаёт бин, отвечающего за маппинг JSON в объекты и наоборот.
     */
    @Bean
    @ConditionalOnMissingBean
    fun objectMapper() = ObjectMapper()
}