package com.shinebrothers.gardendesigner.repository

import com.shinebrothers.gardendesigner.model.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long>
