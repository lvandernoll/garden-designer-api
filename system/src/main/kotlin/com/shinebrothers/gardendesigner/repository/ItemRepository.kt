package com.shinebrothers.gardendesigner.repository

import com.shinebrothers.gardendesigner.model.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long>
