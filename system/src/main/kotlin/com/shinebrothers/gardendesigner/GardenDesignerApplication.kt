package com.shinebrothers.gardendesigner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GardenDesignerApplication

fun main(args: Array<String>) {
	runApplication<GardenDesignerApplication>(*args)
}
