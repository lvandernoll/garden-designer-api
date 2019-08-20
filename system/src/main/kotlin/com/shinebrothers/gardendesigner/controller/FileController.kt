package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.exception.FileStorageException
import com.shinebrothers.gardendesigner.model.FileInfo
import com.shinebrothers.gardendesigner.repository.FileInfoRepository
import com.shinebrothers.gardendesigner.service.FileStorageService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import java.io.IOException

@RestController
@RequestMapping("/api")
class FileController(private val fileInfoRepository: FileInfoRepository) {

    @Autowired
    private val fileStorageService: FileStorageService? = null

    @PostMapping("/files")
    fun uploadFile(@RequestParam("file") file: MultipartFile): FileInfo {
        val fileName = fileStorageService!!.storeFile(file)

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/")
                .path(fileName)
                .toUriString()

        val fileContentType = file.contentType ?: throw FileStorageException("File has no content type")

        return fileInfoRepository.save(FileInfo(fileName = fileName))
    }

    @GetMapping("/files/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        val resource = fileStorageService!!.loadFileAsResource(fileName)

        var contentType: String? = null
        try {
            contentType = request.servletContext.getMimeType(resource.file.absolutePath)
        } catch (ex: IOException) {
            logger.info("Could not determine file type.")
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream"
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.filename + "\"")
            .body(resource)
    }

    @GetMapping("/files")
    fun all(): List<FileInfo> =
        fileInfoRepository.findAll()

    @DeleteMapping("/files/{id}")
    fun deleteFile(@PathVariable id: Long): ResponseEntity<Void> =
        fileInfoRepository.findById(id).map {
            fileStorageService!!.deleteFile(it.fileName)
            fileInfoRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())

    companion object {
        private val logger = LoggerFactory.getLogger(FileController::class.java)
    }
}
