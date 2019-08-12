package com.shinebrothers.gardendesigner.model

import javax.persistence.Embeddable
import javax.persistence.Column
import java.io.Serializable

@Embeddable
data class ItemGardenPlacementId (
    @Column(name = "garden_id")
    val gardenId: Long = 0,

    @Column(name = "item_id")
    val itemId: Long = 0
) : Serializable
