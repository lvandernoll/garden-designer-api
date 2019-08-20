package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.model.Item
import com.shinebrothers.gardendesigner.model.PutItem
import com.shinebrothers.gardendesigner.repository.FileInfoRepository
import com.shinebrothers.gardendesigner.repository.ItemRepository
import com.shinebrothers.gardendesigner.repository.ItemTypeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ItemController(
        private val itemRepository: ItemRepository,
        private val typeRepository: ItemTypeRepository,
        private val fileInfoRepository: FileInfoRepository) {

    @GetMapping("/items")
    fun all(): List<Item> =
        itemRepository.findAll()

    @PostMapping("/items")
    fun create(@Valid @RequestBody createItem: PutItem): ResponseEntity<Item> {
        val type = typeRepository.findByIdOrNull(createItem.type_id) ?: return ResponseEntity.notFound().build()
        val image = fileInfoRepository.findByIdOrNull(createItem.image_id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(itemRepository.save(Item(
            name = createItem.name,
            type = type,
            image = image
        )))
    }

    @GetMapping("/items/{id}")
    fun get(@PathVariable(value = "id") itemId: Long): ResponseEntity<Item> =
        itemRepository.findById(itemId).map { item ->
            ResponseEntity.ok(item)
        }.orElse(ResponseEntity.notFound().build())

    @PutMapping("/items/{id}")
    fun update(@PathVariable(value = "id") itemId: Long,
               @Valid @RequestBody newItem: PutItem): ResponseEntity<Item> {
        val item = itemRepository.findByIdOrNull(itemId)  ?: return ResponseEntity.notFound().build()
        val type = typeRepository.findByIdOrNull(newItem.type_id) ?: return ResponseEntity.notFound().build()
        val image = fileInfoRepository.findByIdOrNull(newItem.image_id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().body(itemRepository.save(Item(
                name = newItem.name,
                type = type,
                image = image
        )))
    }

    @DeleteMapping("/items/{id}")
    fun delete(@PathVariable(value = "id") itemId: Long): ResponseEntity<Void> =
        itemRepository.findById(itemId).map { item  ->
            itemRepository.delete(item)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}
