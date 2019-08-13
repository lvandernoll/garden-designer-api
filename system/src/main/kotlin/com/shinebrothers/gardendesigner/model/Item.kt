package com.shinebrothers.gardendesigner.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Item (
    @Id
    @GeneratedValue
    val id: Long = 0,

    @get: NotBlank
    val name: String = ""
)
