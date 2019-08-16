package com.shinebrothers.gardendesigner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

import com.shinebrothers.gardendesigner.property.FileStorageProperties


@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties::class)
class GardenDesignerApplication

fun main(args: Array<String>) {
	runApplication<GardenDesignerApplication>(*args)
}
