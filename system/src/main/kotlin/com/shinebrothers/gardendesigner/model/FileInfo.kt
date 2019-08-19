package com.shinebrothers.gardendesigner.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class FileInfo (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @get: NotBlank
    val fileName: String = ""
)
