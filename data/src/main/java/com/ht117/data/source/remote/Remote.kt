package com.ht117.data.source.remote

import com.ht117.data.source.local.ConfigLocal
import com.ht117.keylib.NativeLib
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.lang.annotation.Native

object Remote {
    const val Host = "https://oddle-android-challenge-api.herokuapp.com"
    private val jsonConfig = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    fun getClient() = HttpClient(OkHttp) {
        followRedirects = true
        install(ContentNegotiation) {
            json(jsonConfig)
        }
        install(DefaultRequest) {
            if (!headers.contains("Authorization")) {
                header("Authorization", ConfigLocal.getAuthKey())
            }
        }
    }
}


