package ru.blimfy.common.factory

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.env.PropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory

/**
 * Фабрика для чтения файла свойств библиотеки и добавление их в основной файл свойств "application.yml".
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
class CustomConfigPropertiesReaderFactory : PropertySourceFactory {
    @Override
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<out Any> =
        resource.resource.let {
            it.filename.let { filename ->
                val factory = YamlPropertiesFactoryBean()
                factory.setResources(it)
                PropertiesPropertySource(filename!!, factory.getObject()!!)
            }
        }
}