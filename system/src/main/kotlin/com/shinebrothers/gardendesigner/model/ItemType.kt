package com.shinebrothers.gardendesigner.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class ItemType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val name: String = ""
)
