package ru.blimfy.common.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.PageRequest.of
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.by

/**
 * Утилитные методы для работы с базой данных.
 *
 * @author Владислав Кузнецов.
 * @since 0.0.1.
 */
object DatabaseUtils {
    /**
     * Возвращает стандартную конфигурацию пагинации с [pageNumber] и [pageSize].
     */
    fun getDefaultPageable(pageNumber: Int, pageSize: Int): PageRequest =
        of(pageNumber, pageSize, by(DESC, SORT_DEFAULT_FIELD))

    /**
     * Поле сортировки при поиске страницы.
     */
    private const val SORT_DEFAULT_FIELD = "created_date"
}