package ru.liljarn.booker.api.security

import ru.liljarn.booker.api.security.SecurityContextHolder.securityContext
import ru.liljarn.gandalf.user.UserDataResponse

object SecurityContextHolder {
    val securityContext = ThreadLocal<SecurityContext>()
    fun createContext(user: UserDataResponse, jwtToken: String) {
        securityContext.set(SecurityContext(user, jwtToken))
    }
}

class SecurityContext(
    var userInfo: UserDataResponse,
    var userJwt: String
)

val user: UserDataResponse
    get() = securityContext.get()?.userInfo ?: throw IllegalArgumentException("User not authenticated")

val nullableUser: UserDataResponse?
    get() = securityContext.get()?.userInfo
