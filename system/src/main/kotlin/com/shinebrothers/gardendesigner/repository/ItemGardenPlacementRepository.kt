package com.shinebrothers.gardendesigner.repository

import com.shinebrothers.gardendesigner.model.ItemGardenPlacement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemGardenPlacementRepository : JpaRepository<ItemGardenPlacement, Long>
