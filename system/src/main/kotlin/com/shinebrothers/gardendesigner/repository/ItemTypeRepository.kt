package com.shinebrothers.gardendesigner.repository

import com.shinebrothers.gardendesigner.model.ItemType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemTypeRepository : JpaRepository<ItemType, Long>
