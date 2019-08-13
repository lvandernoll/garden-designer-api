package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.ItemGardenPlacement
import com.shinebrothers.gardendesigner.repository.ItemGardenPlacementRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ItemGardenPlacementController(private val itemGardenPlacementRepository: ItemGardenPlacementRepository) {
    @PostMapping("/itemGardenPlacements")
    fun create(@Valid @RequestBody itemGardenPlacement: ItemGardenPlacement): ItemGardenPlacement =
        itemGardenPlacementRepository.save(itemGardenPlacement)

    @PutMapping("/itemGardenPlacements/{id}")
    fun update(@PathVariable(value = "id") itemGardenPlacementId: Long,
               @Valid @RequestBody newItemGardenPlacement: ItemGardenPlacement): ResponseEntity<ItemGardenPlacement> =
        itemGardenPlacementRepository.findById(itemGardenPlacementId).map { existingItemGardenPlacement ->
            val updatedItemGardenPlacement: ItemGardenPlacement = existingItemGardenPlacement.copy(
                x = newItemGardenPlacement.x,
                y = newItemGardenPlacement.y,
                rotation = newItemGardenPlacement.rotation
            )
            ResponseEntity.ok().body(itemGardenPlacementRepository.save(updatedItemGardenPlacement))
        }.orElse(ResponseEntity.notFound().build())


    @DeleteMapping("/itemGardenPlacements/{id}")
    fun delete(@PathVariable(value = "id") itemGardenPlacementId: Long): ResponseEntity<Void> =
        itemGardenPlacementRepository.findById(itemGardenPlacementId).map { itemGardenPlacement  ->
            itemGardenPlacementRepository.delete(itemGardenPlacement)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
