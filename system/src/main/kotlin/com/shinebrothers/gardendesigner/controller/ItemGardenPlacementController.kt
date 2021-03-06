package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.CreateItemGardenPlacement
import com.shinebrothers.gardendesigner.model.ItemGardenPlacement
import com.shinebrothers.gardendesigner.model.PartialItemGardenPlacement
import com.shinebrothers.gardendesigner.model.UpdateItemGardenPlacement
import com.shinebrothers.gardendesigner.repository.GardenRepository
import com.shinebrothers.gardendesigner.repository.ItemGardenPlacementRepository
import com.shinebrothers.gardendesigner.repository.ItemRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ItemGardenPlacementController(
    private val itemGardenPlacementRepository: ItemGardenPlacementRepository,
    private val gardenRepository: GardenRepository,
    private val itemRepository: ItemRepository
) {
    @PostMapping("/itemGardenPlacements")
    fun create(@Valid @RequestBody itemGardenPlacement: CreateItemGardenPlacement): ResponseEntity<PartialItemGardenPlacement> {
        val garden = gardenRepository.findByIdOrNull(itemGardenPlacement.garden_id) ?: return ResponseEntity.notFound().build()
        val item = itemRepository.findByIdOrNull(itemGardenPlacement.item_id) ?: return ResponseEntity.notFound().build()
        val newItemGardenPlacement = itemGardenPlacementRepository.save(ItemGardenPlacement(
            garden = garden,
            item = item,
            x = itemGardenPlacement.x,
            y = itemGardenPlacement.y,
            rotation = itemGardenPlacement.rotation
        ))
        return ResponseEntity.ok().body(PartialItemGardenPlacement(
            id = newItemGardenPlacement.id,
            item = newItemGardenPlacement.item,
            x = newItemGardenPlacement.x,
            y = newItemGardenPlacement.y,
            rotation = newItemGardenPlacement.rotation
        ))
    }

    @PutMapping("/itemGardenPlacements/{id}")
    fun update(@PathVariable(value = "id") itemGardenPlacementId: Long,
               @Valid @RequestBody newItemGardenPlacement: UpdateItemGardenPlacement): ResponseEntity<PartialItemGardenPlacement> =
        itemGardenPlacementRepository.findById(itemGardenPlacementId).map {
            val updatedItemGardenPlacement = ItemGardenPlacement(
                id = it.id,
                garden = it.garden,
                item = it.item,
                x = newItemGardenPlacement.x,
                y = newItemGardenPlacement.y,
                rotation = newItemGardenPlacement.rotation
            )
            itemGardenPlacementRepository.save(updatedItemGardenPlacement)
            ResponseEntity.ok().body(PartialItemGardenPlacement(
                id = updatedItemGardenPlacement.id,
                item = updatedItemGardenPlacement.item,
                x = updatedItemGardenPlacement.x,
                y = updatedItemGardenPlacement.y,
                rotation = updatedItemGardenPlacement.rotation
            ))
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/itemGardenPlacements/{id}")
    fun delete(@PathVariable(value = "id") itemGardenPlacementId: Long): ResponseEntity<Void> =
        itemGardenPlacementRepository.findById(itemGardenPlacementId).map { itemGardenPlacement  ->
            itemGardenPlacementRepository.delete(itemGardenPlacement)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
