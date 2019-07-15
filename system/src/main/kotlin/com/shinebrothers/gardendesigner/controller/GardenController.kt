package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.Garden
import com.shinebrothers.gardendesigner.repository.GardenRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class GardenController(private val gardenRepository: GardenRepository) {

    @GetMapping("/gardens")
    fun all(): List<Garden> =
        gardenRepository.findAll()

    @PostMapping("/gardens")
    fun create(@Valid @RequestBody garden: Garden): Garden =
        gardenRepository.save(garden)

    @GetMapping("/gardens/{id}")
    fun get(@PathVariable(value = "id") gardenId: Long): ResponseEntity<Garden> =
        gardenRepository.findById(gardenId).map { garden ->
            ResponseEntity.ok(garden)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/gardens/{id}")
    fun update(@PathVariable(value = "id") gardenId: Long,
               @Valid @RequestBody newGarden: Garden): ResponseEntity<Garden> =
        gardenRepository.findById(gardenId).map { existingGarden ->
            val updatedGarden: Garden = existingGarden
                .copy(map_coords = newGarden.map_coords)
            ResponseEntity.ok().body(gardenRepository.save(updatedGarden))
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/gardens/{id}")
    fun delete(@PathVariable(value = "id") gardenId: Long): ResponseEntity<Void> =
        gardenRepository.findById(gardenId).map { garden  ->
            gardenRepository.delete(garden)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
