package com.shinebrothers.gardendesigner.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Configuration
@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "file")
class FileStorageProperties {
    var uploadDir: String? = null
}
