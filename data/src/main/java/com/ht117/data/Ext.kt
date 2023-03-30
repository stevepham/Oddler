package com.ht117.data

import android.util.Log
import com.ht117.data.model.AppErr
import com.ht117.data.model.ResultResponse
import com.ht117.data.model.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.net.UnknownHostException

suspend inline fun <reified T, D> FlowCollector<UiState<D>>.getRequest(
    client: HttpClient,
    url: String,
    crossinline converter: (T) -> D
) {
    val response = client.get(urlString = url)
    if (response.status.value in 200..299) {
        val body = response.body<T>()
        emit(UiState.Success(converter.invoke(body)))
    } else {
        processError(response)
    }
}

suspend inline fun <reified R> FlowCollector<UiState<Boolean>>.postRequest(
    client: HttpClient,
    url: String,
    data: R
) {
    val response = client.post(url) {
        contentType(ContentType.Application.Json)
        setBody(data)
    }
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<ResultResponse>()
        if (body.success) {
            emit(UiState.Success(true))
        } else {
            emit(UiState.Failed(AppErr.UnknownErr(Throwable(body.error))))
        }
    } else {
        processError(response)
    }
}

suspend inline fun <reified R> FlowCollector<UiState<Boolean>>.patchRequest(
    client: HttpClient,
    url: String,
    data: R,
) {
    val response = client.patch(url) {
        contentType(ContentType.Application.Json)
        setBody(data)
    }
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<ResultResponse>()
        if (body.success) {
            emit(UiState.Success(true))
        } else {
            emit(UiState.Failed(AppErr.UnknownErr(Throwable(body.error))))
        }
    } else {
        processError(response)
    }
}

suspend inline fun FlowCollector<UiState<Boolean>>.deleteRequest(
    client: HttpClient,
    url: String
) {
    Log.d("DaFuck", "deleting product $url")
    val response = client.delete(urlString = url)
    if (response.status == HttpStatusCode.OK) {
        val body = response.body<ResultResponse>()
        if (body.success) {
            Log.d("DaFuck", "Deleted")
            emit(UiState.Success(true))
        } else {
            Log.d("DaFuck", "Failed ${body.error}")
            emit(UiState.Failed(AppErr.UnknownErr(Throwable(body.error))))
        }
    } else {
        processError(response)
    }
}

suspend inline fun <D> FlowCollector<UiState<D>>.processError(response: HttpResponse) {
    when (response.status) {
        HttpStatusCode.BadRequest -> emit(
            UiState.Failed(
                AppErr.NetErr(
                    400,
                    "Invalid or malformed request"
                )
            )
        )
        HttpStatusCode.Unauthorized -> emit(
            UiState.Failed(
                AppErr.NetErr(
                    401,
                    "Unauthorized request"
                )
            )
        )
        HttpStatusCode.Forbidden -> emit(UiState.Failed(AppErr.NetErr(403, "")))
        HttpStatusCode.Conflict -> emit(
            UiState.Failed(
                AppErr.NetErr(
                    409,
                    "Unable to perform request action because a conflict with current state of resource"
                )
            )
        )
        HttpStatusCode.InternalServerError -> emit(
            UiState.Failed(
                AppErr.NetErr(
                    500,
                    "Internal error"
                )
            )
        )
        else -> emit(
            UiState.Failed(
                AppErr.NetErr(
                    response.status.value,
                    response.status.description
                )
            )
        )
    }
}

fun<T> Flow<UiState<T>>.handleError() = catch {
    when (it) {
        is UnknownHostException -> emit(UiState.Failed(AppErr.NoNetworkErr))
        else -> emit(UiState.Failed(AppErr.UnknownErr(it)))
    }
}
