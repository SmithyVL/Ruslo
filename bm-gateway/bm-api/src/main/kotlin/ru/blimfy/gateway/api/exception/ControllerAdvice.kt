package ru.blimfy.gateway.api.exception

import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail.forStatusAndDetail
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.fromCallable
import ru.blimfy.common.exception.DuplicateException
import ru.blimfy.common.exception.NotFoundException

/**
 * Обработчик исключений контроллеров.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@RestControllerAdvice
class ControllerAdvice : ResponseEntityExceptionHandler() {
    /**
     * Возвращает преобразованный [exchange] с информацией о перехваченной ошибке [ex], указывающей на то, что
     * ресурс уже существует.
     */
    @ExceptionHandler
    suspend fun handleConflict(ex: DuplicateException, exchange: ServerWebExchange): ResponseEntity<in Any> =
        transformException(ex, CONFLICT, exchange)

    /**
     * Возвращает преобразованный [exchange] с информацией о перехваченной ошибке [ex], указывающей на то, что
     * ресурс не найден.
     */
    @ExceptionHandler
    suspend fun handleNotFound(ex: NotFoundException, exchange: ServerWebExchange): ResponseEntity<in Any> =
        transformException(ex, NOT_FOUND, exchange)

    /**
     * Возвращает преобразованный [exchange] с информацией о перехваченной ошибке [ex], указывающей на то, что
     * у пользователя нет доступа к ресурсу или действию с ним.
     */
    @ExceptionHandler
    suspend fun handleAccessDenied(ex: AccessDeniedException, exchange: ServerWebExchange):
            ResponseEntity<in Any> = transformException(ex, FORBIDDEN, exchange)

    /**
     * Возвращает преобразованный [exchange] с информацией о перехваченной ошибке [ex], указывающей на то, что пришли
     * некорректные данные.
     */
    @ExceptionHandler
    suspend fun handleBadRequest(ex: IllegalArgumentException, exchange: ServerWebExchange):
            ResponseEntity<in Any> = transformException(ex, BAD_REQUEST, exchange)

    /**
     * Возвращает преобразованное в ответ со [status] исключение - [ex], для [exchange].
     */
    private suspend fun transformException(
        ex: Exception,
        status: HttpStatus,
        exchange: ServerWebExchange,
    ): ResponseEntity<in Any> =
        super
            .handleExceptionInternal(
                ex,
                forStatusAndDetail(status, ex.message),
                null,
                status,
                exchange,
            )
            .apply { logger.error(ex.message, ex) }
            .awaitSingle()

    override fun handleWebExchangeBindException(
        ex: WebExchangeBindException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        exchange: ServerWebExchange,
    ): Mono<ResponseEntity<in Any>> {
        val transformedEx = runBlocking { transformException(ex, BAD_REQUEST, exchange) }
        return fromCallable { transformedEx }
    }
}