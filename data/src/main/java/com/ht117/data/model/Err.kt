package com.ht117.data.model

sealed class AppErr {
    object NoNetworkErr: AppErr()
    data class NetErr(val code: Int, val message: String): AppErr()
    data class UnknownErr(val throwable: Throwable): AppErr()
}
