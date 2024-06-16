package org.unstpb.wheelshare.api

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.unstpb.wheelshare.service.ImageService
import org.unstpb.wheelshare.service.JwtService
import java.util.UUID

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = ["http://localhost:3000", "https://localhost:3000"], maxAge = 3600)
class ImageController(
    private val imageService: ImageService,
    private val jwtService: JwtService,
) {
    @GetMapping("/{imageId}")
    fun getImages(
        @PathVariable imageId: UUID,
        request: HttpServletRequest,
    ): Any {
        val resource = imageService.getImageAsResource(imageId)
        val contentType = request.contentType ?: "application/octet-stream"

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${resource.filename}\"")
            .body(resource)
    }

    @GetMapping("/cars/{carId}")
    fun getImageIdsForCar(
        @PathVariable carId: UUID,
    ) = imageService.getImagesIdsForCarId(carId)

    @GetMapping("/users/{userId}")
    fun getImageIdForUser(
        @PathVariable userId: UUID,
    ) = imageService.getImagesIdForUserId(userId)

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
    )
    fun uploadImage(
        @RequestParam("images") images: List<MultipartFile>,
        @RequestParam(value = "carId", required = false) carId: UUID?,
        @RequestParam(value = "personId", required = false) personId: UUID?,
    ): ResponseEntity<Any> {
        val imageUploadResponses =
            images.map {
                imageService.upload(it, carId, personId)
            }.toList()

        return ResponseEntity(imageUploadResponses, HttpStatus.OK)
    }
}
