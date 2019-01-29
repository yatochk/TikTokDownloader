package com.yatochk.tiktokdownloader.model

import java.io.File

interface Model {
    fun openAppInMarket(packageName: String)
    fun getCopyUrl(): String?
    fun downloadVideo(url: String, listener: ((String) -> Unit)?)
    fun shareApp()
    fun rate()
    fun getVideoFiles(): ArrayList<File>
}