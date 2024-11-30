package ru.liljarn.booker.support.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("api.internal")
class InternalApiProperties {
    lateinit var header: String
    lateinit var services: Map<String, String>
}

@ConfigurationProperties("api.management")
class ManagementApiProperties {
    lateinit var header: String
    lateinit var key: String
}
