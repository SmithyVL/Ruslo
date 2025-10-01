package tech.ruslo.user.exception

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ProblemDetail.forStatusAndDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.server.ServerWebExchange
import tech.ruslo.exceptions.core.NotFoundException

/**
 * Стандартный обработчик исключений контроллеров.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
@RestControllerAdvice
class DefaultControllerAdvice : ResponseEntityExceptionHandler() {
    /**
     * Возвращает преобразованный [exchange] с информацией о перехваченной ошибке [ex], указывающей на то, что ресурс не
     * найден.
     */
    @ExceptionHandler
    suspend fun handleNotFound(ex: NotFoundException, exchange: ServerWebExchange): ResponseEntity<in Any> =
        transformException(ex, NOT_FOUND, exchange)

    /**
     * Возвращает преобразованный [exchange] с информацией о перехваченной ошибке [ex], указывающей на то, что ресурс
     * уже существует.
     */
    @ExceptionHandler
    suspend fun handleConflict(ex: DuplicateKeyException, exchange: ServerWebExchange): ResponseEntity<in Any> =
        transformException(ex, CONFLICT, exchange)

    /**
     * Возвращает преобразованное в ответ со [status] исключение - [ex], для [exchange].
     */
    private suspend fun transformException(
        ex: Exception,
        status: HttpStatus,
        exchange: ServerWebExchange,
    ): ResponseEntity<in Any> = super
        .handleExceptionInternal(
            ex,
            forStatusAndDetail(status, ex.message),
            null,
            status,
            exchange,
        )
        .apply { logger.error(ex.message, ex) }
        .awaitSingle()
}