package com.shinebrothers.gardendesigner.model

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.JoinColumn
import javax.validation.constraints.NotBlank
import javax.persistence.EmbeddedId

@Entity
data class ItemGardenPlacement (
    @EmbeddedId
    val id: ItemGardenPlacementId,

    @ManyToOne
    @MapsId("item_id")
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne
    @MapsId("garden_id")
    @JoinColumn(name = "garden_id")
    val garden: Garden,

    @get: NotBlank
    var x: Int = 0,

    @get: NotBlank
    var y: Int = 0,

    @get: NotBlank
    var rotation: Int = 0
)
