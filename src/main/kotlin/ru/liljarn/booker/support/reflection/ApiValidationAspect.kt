package ru.liljarn.booker.support.reflection

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class ApiValidationAspect(
    private val validationHandler: ValidationHandler
) {

    @Pointcut("within(@ru.liljarn.booker.support.reflection.ManagementApi *)")
    fun classWithManagementApiAnnotation() {
    }

    @Before("classWithManagementApiAnnotation()")
    fun checkHeaderBeforeMethod(joinPoint: JoinPoint) {
        val request = (RequestContextHolder.currentRequestAttributes() as? ServletRequestAttributes)?.request

        if (request != null && !validationHandler.validateHeader(request)) {
            throw InvalidHeaderException("Invalid or missing management API header")
        }
    }

    @Pointcut("@annotation(internalApi)")
    fun methodWithInternalApiAnnotation(internalApi: InternalApi) {}

    @Before("@annotation(internalApi)")
    fun checkHeaderBeforeMethod(joinPoint: JoinPoint, internalApi: InternalApi) {
        val request = (RequestContextHolder.currentRequestAttributes() as? ServletRequestAttributes)?.request

        if (request != null && !validationHandler.validateInternalHeader(request, internalApi)) {
            throw InvalidHeaderException("Invalid or missing internal API header")
        }
    }
}

@ResponseStatus(HttpStatus.FORBIDDEN)
class InvalidHeaderException(message: String) : RuntimeException(message)
