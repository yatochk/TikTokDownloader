package com.yatochk.tiktokdownloader.utils

import android.os.Build

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL

    return if (model.startsWith(manufacturer)) model
    else "$manufacturer $model"
}