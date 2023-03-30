package com.ht117.keylib

object NativeLib {

    init {
        System.loadLibrary("keylib")
    }

    external fun getAccountID(): String
    external fun getAuthKey(): String
}
