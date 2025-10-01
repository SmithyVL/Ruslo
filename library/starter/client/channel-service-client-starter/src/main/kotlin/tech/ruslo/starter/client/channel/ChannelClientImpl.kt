package tech.ruslo.starter.client.channel

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow
import tech.ruslo.channel.dto.ChannelClient
import tech.ruslo.channel.dto.ChannelDto

/**
 * Реализация клиентского интерфейса для работы с информацией о каналах из сервиса каналов.
 *
 * @property channelWebClient настроенный клиент для запросов в сервис каналов.
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@Service
class ChannelClientImpl(val channelWebClient: WebClient) : ChannelClient {
    override suspend fun saveChannel(channelDto: ChannelDto) = channelWebClient
        .post()
        .uri(CHANNELS_PATH_PREFIX)
        .bodyValue(channelDto)
        .retrieve()
        .awaitBody<ChannelDto>()

    override fun saveChannels(channelDtos: List<ChannelDto>) = channelWebClient
        .post()
        .uri("$CHANNELS_PATH_PREFIX/batch")
        .bodyValue(channelDtos)
        .retrieve()
        .bodyToFlow<ChannelDto>()

    override suspend fun deleteChannel(id: Long) = channelWebClient
        .delete()
        .uri("$CHANNELS_PATH_PREFIX/{id}", id)
        .retrieve()
        .awaitBody<Unit>()

    private companion object {
        /**
         * Префикс для запросов о каналах.
         */
        private const val CHANNELS_PATH_PREFIX = "/channels"
    }
}