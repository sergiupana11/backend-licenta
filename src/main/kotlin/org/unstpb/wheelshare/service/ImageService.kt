package org.unstpb.wheelshare.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.unstpb.wheelshare.dto.FileUploadResponse
import org.unstpb.wheelshare.entity.ImageDetails
import org.unstpb.wheelshare.exception.FileNotSupportedException
import org.unstpb.wheelshare.exception.ImageNotFoundException
import org.unstpb.wheelshare.repository.ImageRepository
import org.unstpb.wheelshare.repository.UserRepository
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.UUID

private val UPLOAD_PATH = Paths.get("${ClassPathResource("").file.absolutePath}${File.separator}static${File.separator}image")

@Service
class ImageService(
    private val imageRepository: ImageRepository,
    private val userRepository: UserRepository,
    private val logger: Logger = LoggerFactory.getLogger(ImageService::class.java),
) {
    fun getImageAsResource(imageId: UUID): Resource {
        val imagePath = UPLOAD_PATH.resolve(imageId.toString()).normalize()
        val resource = UrlResource(imagePath.toUri())
        if (resource.exists()) {
            return resource
        }
        throw FileNotFoundException()
    }

    fun getImagesIdsForCarId(carId: UUID) =
        imageRepository.findAllByCarId(carId)
            .map {
                it.id
            }

    @Transactional
    fun upload(
        image: MultipartFile,
        carId: UUID? = null,
        personId: UUID? = null,
    ): FileUploadResponse {
        if (!Files.exists(UPLOAD_PATH)) {
            Files.createDirectories(UPLOAD_PATH)
        }

        if (image.contentType != "image/jpeg" && image.contentType != "image/png" && image.contentType != "image/jpg") {
            throw FileNotSupportedException()
        }

        val imageId = UUID.randomUUID()

        val imagePath = UPLOAD_PATH.resolve(imageId.toString())
        Files.copy(image.inputStream, imagePath)

        val fileUri =
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("image/").path(imageId.toString()).toUriString()

        val fileDownloadUri =
            ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/download/").path(imageId.toString()).toUriString()

        val imageDetails =
            ImageDetails(
                imageId,
                carId,
                personId,
                fileUri,
                fileDownloadUri,
                image.size,
            )

        imageRepository.save(imageDetails)

        return FileUploadResponse(
            imageDetails.id,
            fileUri,
            fileDownloadUri,
            image.size,
        )
    }

    fun getImagesIdForUserId(userId: UUID): UUID = imageRepository.findByPersonId(userId)?.id ?: throw ImageNotFoundException()
}
