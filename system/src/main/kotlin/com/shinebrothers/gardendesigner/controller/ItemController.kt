package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.Item
import com.shinebrothers.gardendesigner.repository.ItemRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ItemController(private val itemRepository: ItemRepository) {

    @GetMapping("/items")
    fun all(): List<Item> =
        itemRepository.findAll()

    @PostMapping("/items")
    fun create(@Valid @RequestBody item: Item): Item =
        itemRepository.save(item)

    @GetMapping("/items/{id}")
    fun get(@PathVariable(value = "id") itemId: Long): ResponseEntity<Item> =
        itemRepository.findById(itemId).map { item ->
            ResponseEntity.ok(item)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/items/{id}")
    fun update(@PathVariable(value = "id") itemId: Long,
               @Valid @RequestBody newItem: Item): ResponseEntity<Item> =
        itemRepository.findById(itemId).map { existingItem ->
            val updatedItem: Item = existingItem.copy(name = newItem.name)
            ResponseEntity.ok().body(itemRepository.save(updatedItem))
        }.orElse(ResponseEntity.notFound().build())

    @DeleteMapping("/items/{id}")
    fun delete(@PathVariable(value = "id") itemId: Long): ResponseEntity<Void> =
        itemRepository.findById(itemId).map { item  ->
            itemRepository.delete(item)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
