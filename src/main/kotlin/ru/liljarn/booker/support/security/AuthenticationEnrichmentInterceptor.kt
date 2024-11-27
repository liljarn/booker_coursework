package ru.liljarn.booker.support.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import ru.liljarn.booker.support.security.SecurityContextHolder.createContext
import ru.liljarn.booker.infrastructure.grpc.GandalfService

@Component
class AuthenticationEnrichmentInterceptor(
    private val gandalfService: GandalfService
) : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean =
        runCatching {
            if (request.getHeader("Authorization") != null) {
                val token = request.getHeader("Authorization").replace("Bearer ", "")
                createContext(gandalfService.getUserByToken(token), token)
            }
            return super.preHandle(request, response, handler)
        }.getOrElse {
            response.status = HttpStatus.UNAUTHORIZED.value()
            false
        }
}