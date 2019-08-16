package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.ItemType
import com.shinebrothers.gardendesigner.repository.ItemTypeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ItemTypeController(private val itemTypeRepository: ItemTypeRepository) {

    @GetMapping("/itemTypes")
    fun all(): List<ItemType> =
        itemTypeRepository.findAll()

    @PostMapping("/itemTypes")
    fun create(@Valid @RequestBody itemType: ItemType): ItemType =
        itemTypeRepository.save(itemType)

    @PutMapping("/itemTypes/{id}")
    fun update(@PathVariable(value = "id") itemTypeId: Long,
               @Valid @RequestBody newItemType: ItemType): ResponseEntity<ItemType> =
        itemTypeRepository.findById(itemTypeId).map { existingItemType ->
            val updatedItemType: ItemType = existingItemType.copy(name = newItemType.name)
            ResponseEntity.ok().body(itemTypeRepository.save(updatedItemType))
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/itemTypes/{id}")
    fun delete(@PathVariable(value = "id") itemTypeId: Long): ResponseEntity<Void> =
        itemTypeRepository.findById(itemTypeId).map { itemType  ->
            itemTypeRepository.delete(itemType)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
