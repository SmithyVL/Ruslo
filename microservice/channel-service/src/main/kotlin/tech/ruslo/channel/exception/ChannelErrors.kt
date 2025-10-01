package tech.ruslo.channel.exception

/**
 * Сообщения ошибок, связанных с информацией о каналах.
 *
 * @param msg текст ошибки.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
enum class ChannelErrors(val msg: String) {
    /**
     * Сообщение не найдено.
     */
    MESSAGE_NOT_FOUND("Message not found!"),
}