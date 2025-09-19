package tech.ruslo.domain.converter

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.postgresql.codec.Json.of
import java.util.UUID
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

/**
 * Ссылка на сообщение.
 *
 * @property type тип ссылки.
 * @property messageId идентификатор сообщения.
 * @property channelId идентификатор канала.
 * @property serverId идентификатор сервера.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageReference(
    val type: MessageReferenceTypes,
    val messageId: UUID,
    val channelId: UUID,
    val serverId: UUID? = null,
)

/**
 * Типы ссылок на сообщения.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class MessageReferenceTypes {
    /**
     * Стандартная ссылка, используемая при ответах.
     */
    DEFAULT,

    /**
     * Ссылка используемая для репостов.
     */
    FORWARD,
}

/**
 * Конвертер для преобразования информации о ссылке на сообщение в JSON поле.
 *
 * @property objectMapper маппер для преобразования JSON.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@WritingConverter
class MessageReferenceWriteConverter(private val objectMapper: ObjectMapper) : Converter<MessageReference, Json> {
    override fun convert(source: MessageReference): Json =
        of(objectMapper.writeValueAsString(source))
}

/**
 * Конвертер для преобразования JSON поля в информацию о ссылке на сообщение.
 *
 * @property objectMapper маппер для преобразования JSON.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ReadingConverter
class MessageReferenceReadConverter(private val objectMapper: ObjectMapper) : Converter<Json, MessageReference> {
    override fun convert(source: Json): MessageReference =
        objectMapper.readValue(source.asString(), MessageReference::class.java)
}