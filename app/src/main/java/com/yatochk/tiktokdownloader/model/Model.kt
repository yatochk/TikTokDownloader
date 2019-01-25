package com.yatochk.tiktokdownloader.model

interface Model {
    fun openAppInMarket(packageName: String)
    fun getCopyUrl(): String?
    fun downloadVideo(url: String)
    fun shareApp()
    fun rate()
}