package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.Garden
import com.shinebrothers.gardendesigner.model.CreateGarden
import com.shinebrothers.gardendesigner.model.PartialItemGardenPlacement
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
    fun all(): List<Map<String, Any>> =
        gardenRepository.findAll().map {
            mapOf("id" to it.id, "name" to it.name)
        }

    @PostMapping("/gardens")
    fun create(@Valid @RequestBody garden: CreateGarden): Garden =
        gardenRepository.save(Garden(name = garden.name, map_coords = garden.map_coords))

    @GetMapping("/gardens/{id}")
    fun get(@PathVariable(value = "id") gardenId: Long): ResponseEntity<Map<String, Any>> =
        gardenRepository.findById(gardenId).map { it ->
            val items: List<PartialItemGardenPlacement> = it.items.map {
                PartialItemGardenPlacement(
                    id = it.id,
                    item = it.item,
                    x = it.x,
                    y = it.y,
                    rotation = it.rotation
                )
            }
            ResponseEntity.ok(mapOf(
                "id" to it.id,
                "name" to it.name,
                "map_coords" to it.map_coords,
                "items" to items)
            )
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/gardens/{id}")
    fun update(@PathVariable(value = "id") gardenId: Long,
               @Valid @RequestBody newGarden: Garden): ResponseEntity<Map<String, Any>> =
        gardenRepository.findById(gardenId).map { existingGarden ->
            val updatedGarden: Garden = existingGarden.copy(
                name = newGarden.name,
                map_coords = newGarden.map_coords
            )
            ResponseEntity.ok().body(gardenRepository.save(updatedGarden))
            this.get(gardenId)
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/gardens/{id}")
    fun delete(@PathVariable(value = "id") gardenId: Long): ResponseEntity<Void> =
        gardenRepository.findById(gardenId).map { garden  ->
            gardenRepository.delete(garden)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
