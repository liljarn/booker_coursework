package ru.liljarn.booker.api.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthorizationConfig(
    private val authenticationEnrichmentInterceptor: AuthenticationEnrichmentInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationEnrichmentInterceptor)
        super.addInterceptors(registry)
    }
}