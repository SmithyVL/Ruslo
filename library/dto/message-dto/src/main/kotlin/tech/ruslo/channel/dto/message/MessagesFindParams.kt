package tech.ruslo.channel.dto.message

/**
 * Параметры поиска страницы с сообщениями канала.
 *
 * @property channelId идентификатор канала.
 * @property around идентификатор автора сообщения.
 * @property before тип сообщения.
 * @property after содержимое сообщения.
 * @property limit дата создания в UTC.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
data class MessagesFindParams(
    val channelId: Long,
    val around: Long? = null,
    val before: Long? = null,
    val after: Long? = null,
    val limit: Int = 50,
)