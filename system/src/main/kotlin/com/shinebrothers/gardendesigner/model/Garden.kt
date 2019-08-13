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
    @GeneratedValue
    val id: Long = 0,

    @get: NotBlank
    val map_coords: String = "", // Should be some kind of array of objects [{x: 14, y: 52},]

    @OneToMany(mappedBy = "garden")
    val itemGardenPlacements: Set<ItemGardenPlacement>
)
