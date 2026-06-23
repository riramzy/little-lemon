package com.riramzy.littlelemon.data.remote

import android.util.Log
import com.riramzy.littlelemon.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {
    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun fetchMenu(): NetworkMenu? {
        return try {
            val responseText: String = httpClient
                .get(Constants.MENU_URL)
                .bodyAsText()

            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.decodeFromString(NetworkMenu.serializer(), responseText)

        } catch (e: Exception) {
            Log.e("NetworkClient", "Error fetching menu: ${e.message}")
            null
        }
    }
}