package ru.liljarn.booker.support.mapper

import ru.liljarn.booker.api.security.userId
import ru.liljarn.booker.domain.model.dto.UserData
import ru.liljarn.gandalf.user.UserDataResponse
import java.time.Instant
import java.time.ZoneOffset

fun UserDataResponse.toUserData(): UserData = UserData(
    userId = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    birthDate = Instant.ofEpochSecond(birthdate.seconds, birthdate.nanos.toLong())
        .atZone(ZoneOffset.UTC)
        .toLocalDate(),
    photoUrl = photoUrl
)
