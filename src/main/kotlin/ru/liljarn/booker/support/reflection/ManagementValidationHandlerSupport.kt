package ru.liljarn.booker.support.reflection

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

private const val HEADER_NAME = "X-MANAGEMENT-API"

@Service
class ManagementValidationHandlerSupport(
    @Value("\${api.management.key}")
    private val managementKey: String
) {

    fun validateHeader(request: HttpServletRequest): Boolean {
        val managementRequestKey = request.getHeader(HEADER_NAME)
        return managementRequestKey == managementKey
    }
}
