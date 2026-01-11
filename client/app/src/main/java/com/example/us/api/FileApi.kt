package com.example.us.api

import android.content.Context
import android.net.Uri
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.Serializable

object FileApi {

    suspend fun uploadDiaryImage(
        context: Context,
        uri: Uri
    ): ImageUploadResponse {

        val bytes = context.contentResolver
            .openInputStream(uri)
            ?.readBytes()
            ?: error("Cannot read image")

        return ApiClient.client.submitFormWithBinaryData(
            url = "${ApiClient.API_URL}/files/diary-image",
            formData = formData {
                append(
                    "image",
                    bytes,
                    Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                        append(
                            HttpHeaders.ContentDisposition,
                            "filename=\"image.jpg\""
                        )
                    }
                )
            }
        ).body()
    }
}

@Serializable
data class ImageUploadResponse(
    val smallUrl: String,
    val largeUrl: String
)