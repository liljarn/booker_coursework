package ru.liljarn.booker.jobs.handler

import ru.liljarn.booker.domain.model.type.DailyJobType

interface DailyStatusProcessor {
    val event: DailyJobType

    fun process()
}
