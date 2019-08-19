package com.shinebrothers.gardendesigner.service

import com.shinebrothers.gardendesigner.exception.FileStorageException
import com.shinebrothers.gardendesigner.exception.MyFileNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.UUID
import org.apache.commons.io.FilenameUtils

@Service
class FileStorageService(@Value("\${file.upload-dir}") private var uploadDir: String) {

    private val fileStorageLocation: Path = Paths.get(uploadDir)
        .toAbsolutePath().normalize()

    @Autowired
    fun FileStorageService() {
        try {
            Files.createDirectories(fileStorageLocation)
        } catch (ex: Exception) {
            throw FileStorageException("Could not create the directory where the uploaded files will be stored.", ex)
        }
    }

    fun storeFile(file: MultipartFile): String {
        // Make filename unique
        val contentType = FilenameUtils.getExtension(file.originalFilename)

        val fileName = "${UUID.randomUUID()}.$contentType"

        try {
            // Copy file to the target location (Replacing existing file with the same name)
            val targetLocation: Path = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

            return fileName
        } catch (ex: IOException) {
            throw FileStorageException("Could not store file " + fileName + ". Please try again!", ex)
        }
    }

    fun deleteFile(fileName: String): Boolean {
        try {
            val filePath: Path = fileStorageLocation.resolve(fileName).normalize()
            try {
                Files.delete(filePath)
                return true
            } catch(ex: IOException) {
                throw FileStorageException("Could not delete file " + fileName + ". Please try again!", ex)
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found " + fileName, ex)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        try {
            val filePath: Path = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if(resource.exists()) {
                return resource
            } else {
                throw MyFileNotFoundException("File not found " + fileName)
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found " + fileName, ex)
        }
    }
}
