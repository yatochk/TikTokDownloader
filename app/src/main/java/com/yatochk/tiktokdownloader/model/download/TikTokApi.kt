package com.yatochk.tiktokdownloader.model.download

interface TikTokApi {
    fun downloadVideo(url: String, listener: ((ByteArray) -> Unit)?)
}