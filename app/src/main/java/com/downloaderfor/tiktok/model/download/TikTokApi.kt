package com.downloaderfor.tiktok.model.download

interface TikTokApi {
    fun downloadVideo(url: String, listener: ((ByteArray) -> Unit)?)
}