package com.shinebrothers.gardendesigner.controller

import com.shinebrothers.gardendesigner.exception.FileStorageException
import com.shinebrothers.gardendesigner.payload.UploadFileResponse
import com.shinebrothers.gardendesigner.service.FileStorageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import java.io.IOException
import java.util.Arrays
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class FileController {

    @Autowired
    private val fileStorageService: FileStorageService? = null

    @PostMapping("/files")
    fun uploadFile(@RequestParam("file") file: MultipartFile): UploadFileResponse {
        val fileName = fileStorageService!!.storeFile(file)

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString()

        val fileContentType = file.contentType ?: throw FileStorageException("File has no content type")

        return UploadFileResponse(fileName, fileDownloadUri, fileContentType, file.size)
    }

    @GetMapping("/files/{fileName:.+}")
    fun downloadFile(@PathVariable fileName: String, request: HttpServletRequest): ResponseEntity<Resource> {
        // Load file as Resource
        val resource = fileStorageService!!.loadFileAsResource(fileName)

        // Try to determine file's content type
        var contentType: String? = null
        try {
            contentType = request.servletContext.getMimeType(resource.getFile().getAbsolutePath())
        } catch (ex: IOException) {
            logger.info("Could not determine file type.")
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream"
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FileController::class.java)
    }
}
