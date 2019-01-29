package com.yatochk.tiktokdownloader.model.download

import android.os.Handler
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class ServerTasks : TikTokApi {
    override fun downloadVideo(url: String, listener: ((ByteArray) -> Unit)?) {
        val handler = Handler()
        thread {
            var videoFile: ByteArray? = null
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()
                val redirectStream = urlConnection.inputStream
                val redirectReader = BufferedReader(InputStreamReader(redirectStream))

                var htmlUrlString = ""
                for (line in redirectReader.readLines()) {
                    if (line.contains("target")) {
                        htmlUrlString = line.substring(line.indexOf("href=\""))
                        htmlUrlString = htmlUrlString.substring(6, htmlUrlString.indexOf("\">"))
                    }
                }

                Log.d("FinalUrl", htmlUrlString)
                val connection = URL(htmlUrlString).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))

                var urlString = ""
                reader.readLines().forEach {
                    urlString += it
                }

                var videoID = urlString.substring(urlString.indexOf("video_id"))
                videoID = videoID.substring(9, videoID.indexOf("\\"))
                Log.d("FinalUrl", "Video id: $videoID")

                val finalUrl =
                    "http://api2.musical.ly/aweme/v1/play/?video_id=$videoID&line=0&ratio=720p&media_type=4&vr_type=0&test_cdn=None&improve_bitrate=0"
                val finalUrlConnection = URL(finalUrl).openConnection() as HttpURLConnection
                finalUrlConnection.requestMethod = "GET"
                finalUrlConnection.connect()
                val finalInputStream = finalUrlConnection.inputStream
                videoFile = finalInputStream.readBytes()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                handler.post {
                    if (videoFile != null)
                        listener?.invoke(videoFile)
                }
            }
        }
    }
}