package tech.ruslo.websocket.dto.event.read

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME

/**
 * Интерфейс-маркер для событий, приходящих через WS для чтения и последующей обработки.
 *
 * @property type тип события.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes(JsonSubTypes.Type(IdentifyDto::class, "IDENTIFY"))
interface ReadEvent {
    var type: String
}