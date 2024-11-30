package ru.liljarn.booker.infrastructure.support.properties

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kafka")
class BookerKafkaProperties {
    var clusters: Map<String, KafkaProperties> = mutableMapOf()
}
