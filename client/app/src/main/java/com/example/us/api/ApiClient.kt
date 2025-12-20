package com.example.us.api

import android.net.http.HttpResponseCache
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
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
    }


    const val BASE_URL = "http://192.168.0.109:8080/api"

    // --- универсальный GET ---
    suspend inline fun <reified T> get(path: String): T {
        return client.get("$BASE_URL/$path").body()
    }

    // --- универсальный POST ---
    suspend inline fun <reified T> post(path: String, body: Any): T {
        return client.post("$BASE_URL/$path") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

    // --- POST without body
    suspend inline fun <reified T> post(path: String): T {
        return client.post("$BASE_URL/$path").body()
    }

    // --- PUT ---
    suspend inline fun <reified T> put(path: String, body: Any): T {
        return client.put("$BASE_URL/$path") {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }

    // --- DELETE ---
    suspend inline fun <reified T> delete(path: String): T {
        return client.delete("$BASE_URL/$path").body()
    }

}