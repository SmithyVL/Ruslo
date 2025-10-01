package tech.ruslo.channel.dto.enumerations

/**
 * Типы каналов.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ChannelTypes() {
    /**
     * Текстовый канал.
     */
    TEXT,

    /**
     * Голосовой канал.
     */
    VOICE,

    /**
     * Категория для каналов.
     */
    CATEGORY,
}