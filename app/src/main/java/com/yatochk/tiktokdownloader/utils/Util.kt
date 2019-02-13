package com.yatochk.tiktokdownloader.utils

import android.os.Build
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.fragment.app.FragmentActivity

fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL

    return if (model.startsWith(manufacturer)) model
    else "$manufacturer $model"
}

fun setSystemBarColor(act: FragmentActivity, @ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = act.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = act.resources.getColor(color)
    }
}