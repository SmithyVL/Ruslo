package tech.ruslo.domain.converter

import com.fasterxml.jackson.databind.ObjectMapper
import io.r2dbc.postgresql.codec.Json
import io.r2dbc.postgresql.codec.Json.of
import java.time.Instant
import java.util.UUID
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import tech.ruslo.domain.converter.enumerations.MessageTypes
import tech.ruslo.domain.converter.enumerations.MessageTypes.DEFAULT

/**
 * Моментальный снимок сообщения.
 *
 * @property content содержимое.
 * @property authorId идентификатор автора.
 * @property createdDate дата создания.
 * @property type тип.
 * @property mentions идентификаторы упомянутых пользователей.
 * @property updatedDate дата обновления.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessageSnapshot(
    val content: String,
    val authorId: UUID,
    val createdDate: Instant,
    val type: MessageTypes = DEFAULT,
    val mentions: Set<UUID>? = null,
    val updatedDate: Instant? = null,
)

/**
 * Конвертер для преобразования информации о моментальном снимке сообщения в JSON поле.
 *
 * @property objectMapper маппер для преобразования JSON.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@WritingConverter
class MessageSnapshotWriteConverter(private val objectMapper: ObjectMapper) : Converter<MessageSnapshot, Json> {
    override fun convert(source: MessageSnapshot): Json =
        of(objectMapper.writeValueAsString(source))
}

/**
 * Конвертер для преобразования JSON поля в информацию о моментальном снимке сообщения.
 *
 * @property objectMapper маппер для преобразования JSON.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@ReadingConverter
class MessageSnapshotReadConverter(private val objectMapper: ObjectMapper) : Converter<Json, MessageSnapshot> {
    override fun convert(source: Json): MessageSnapshot =
        objectMapper.readValue(source.asString(), MessageSnapshot::class.java)
}