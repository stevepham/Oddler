package com.ht117.data.source.local

import com.ht117.keylib.NativeLib

object ConfigLocal {

    fun getAccountID() = NativeLib.getAccountID()

    fun getAuthKey() = NativeLib.getAuthKey()
}
