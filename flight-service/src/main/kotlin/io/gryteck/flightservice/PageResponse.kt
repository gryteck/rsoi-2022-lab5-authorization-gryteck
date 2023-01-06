package io.gryteck.flightservice

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val page: Int,
    val pageSize: Int,
    val totalElements: Long,
    val items: List<T>
)

fun <T> Page<T>.toPageResponse(): PageResponse<T> = PageResponse(
    page = pageable.pageNumber + 1,
    pageSize = pageable.pageSize,
    totalElements = totalElements,
    items = content
)