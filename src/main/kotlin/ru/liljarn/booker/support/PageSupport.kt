package ru.liljarn.booker.support

import org.springframework.data.domain.PageRequest

private fun getPageRequest(page: Int) = PageRequest.of(page, 20)

fun <T> pageRequest(page: Int, body: (PageRequest) -> T) = body(getPageRequest(page))
