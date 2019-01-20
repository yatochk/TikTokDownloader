package com.yatochk.tiktokdownloader.model.download

import java.net.HttpURLConnection
import java.net.URL

class ServerTasks : TikTokApi {
    override fun downloadVideo(url: String, listener: ((String) -> Unit)?) {
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"
        urlConnection.connect()
        val inputStream = urlConnection.inputStream
        val byteFile = ByteArray(inputStream.available())
        inputStream.read(byteFile)

        val fileName = ""
        listener?.invoke(fileName)
    }
}