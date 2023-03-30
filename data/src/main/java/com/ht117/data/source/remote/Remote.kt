package com.ht117.data.source.remote

import android.util.Log
import com.ht117.data.source.local.ConfigLocal
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.BuildConfig

object Remote {
    const val Host = "https://oddle-android-challenge-api.herokuapp.com"
    private val jsonConfig = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    fun getClient(): HttpClient {
        val client = HttpClient(OkHttp) {
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

        client.plugin(HttpSend).intercept { req ->
            val call = execute(req)
            if (BuildConfig.DEBUG) {
                Log.d("DaFuck", call.response.bodyAsText())
            }
            call
        }

        return client
    }
}


