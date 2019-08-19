package com.shinebrothers.gardendesigner.repository

import com.shinebrothers.gardendesigner.model.FileInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FileInfoRepository : JpaRepository<FileInfo, Long>
