package com.yatochk.tiktokdownloader.model.download

import android.os.Handler
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ServerTasks : TikTokApi {
    override fun downloadVideo(url: String, listener: ((ByteArray) -> Unit)?) {
        val handler = Handler()
        thread {
            val urlConnection = URL(url).openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()
            val inputStream = urlConnection.inputStream
            val byteFile = ByteArray(inputStream.available())
            inputStream.read(byteFile)

            handler.post {
                listener?.invoke(byteFile)
            }
        }.start()
    }
}