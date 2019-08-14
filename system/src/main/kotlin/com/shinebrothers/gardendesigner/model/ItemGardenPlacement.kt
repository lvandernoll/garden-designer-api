package com.shinebrothers.gardendesigner.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.validation.constraints.NotNull

@Entity
data class ItemGardenPlacement (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne
    @JoinColumn(name = "garden_id")
    val garden: Garden,

    @get: NotNull
    var x: Int = 0,

    @get: NotNull
    var y: Int = 0,

    @get: NotNull
    var rotation: Int = 0
)

class CreateItemGardenPlacement(
    val item_id: Long,
    val garden_id: Long,
    val x: Int,
    val y: Int,
    val rotation: Int
)

class PartialItemGardenPlacement(
    val id: Long,
    val item: Item,
    val x: Int,
    val y: Int,
    val rotation: Int
)
