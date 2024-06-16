package org.unstpb.wheelshare.dto

import java.util.UUID

data class FileUploadResponse(val id: UUID, val fileUri: String, val fileDownloadUri: String, val size: Long)
