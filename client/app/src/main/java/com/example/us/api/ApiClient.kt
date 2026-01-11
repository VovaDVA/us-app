package com.example.us.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiClient {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        // 🔐 Добавляем Authorization ко всем запросам автоматически
        defaultRequest {
            AuthHolder.token?.let { token ->
                header("Authorization", "Bearer $token")
            }
        }
    }

    const val BASE_URL = "http://192.168.0.109:8080"
    const val API_URL = "$BASE_URL/api"

    // --- универсальный GET ---
    suspend inline fun <reified T> get(path: String): T =
        client.get("$API_URL/$path").body()

    // --- универсальный POST с телом ---
    suspend inline fun <reified T> post(path: String, body: Any): T =
        client.post("$API_URL/$path") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    // --- POST без тела ---
    suspend inline fun <reified T> post(path: String): T =
        client.post("$API_URL/$path").body()

    // --- универсальный PUT ---
    suspend inline fun <reified T> put(path: String, body: Any): T =
        client.put("$API_URL/$path") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()

    // --- универсальный DELETE ---
    suspend inline fun <reified T> delete(path: String): T =
        client.delete("$API_URL/$path").body()
}
