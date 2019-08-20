package com.shinebrothers.gardendesigner.model

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class Item (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val name: String = "",

    @ManyToOne
    val type: ItemType,

    @ManyToOne
    val image: FileInfo
)

class PutItem (
    @get: NotBlank
    val name: String = "",

    @get: NotNull
    val type_id: Long,

    @get: NotNull
    val image_id: Long
)
