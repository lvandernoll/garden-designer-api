package com.shinebrothers.gardendesigner.repository

import com.shinebrothers.gardendesigner.model.Garden
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GardenRepository : JpaRepository<Garden, Long>
