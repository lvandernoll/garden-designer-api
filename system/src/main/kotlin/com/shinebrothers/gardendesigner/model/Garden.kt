package com.shinebrothers.gardendesigner.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.OneToMany
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Garden (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val name: String = "",

    @get: NotBlank
    val map_coords: String = "", // Should be some kind of array of objects [{x: 14, y: 52},]

    @OneToMany(mappedBy = "garden")
    val items: List<ItemGardenPlacement> = listOf()
)

data class CreateGarden (
    @get: NotBlank
    val name: String = "",

    @get: NotBlank
    val map_coords: String = "" // Should be some kind of array of objects [{x: 14, y: 52},]
)
