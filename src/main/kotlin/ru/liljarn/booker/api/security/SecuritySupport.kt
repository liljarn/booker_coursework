package ru.liljarn.booker.api.security

import ru.liljarn.gandalf.user.UserDataResponse
import java.util.UUID

fun <T> userContext(body: (UserDataResponse) -> T) = body(user)

val UserDataResponse.userId: UUID
    get() = UUID.fromString(uuid)
