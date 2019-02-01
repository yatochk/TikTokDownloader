package com.yatochk.tiktokdownloader.model

import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.model.db.StorageApi
import com.yatochk.tiktokdownloader.model.download.TikTokApi
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class ModelImpl(
    private val context: Context,
    private val tikTokApi: TikTokApi,
    private val storageApi: StorageApi
) : Model {

    override fun deleteVideo(path: String, listener: ((Boolean) -> Unit)?) {
        storageApi.deleteFile(path) {
            App.component.galleryPresenter.update()
            listener?.invoke(it)
        }
    }

    companion object {
        const val SUCCESS_DOWNLOAD = 200
        const val ERROR_DOWNLOAD = 404
    }

    override fun downloadPreview(url: String, view: ImageView, listener: ((code: Int) -> Unit)?) {
        val handler = Handler()
        thread {
            val htmlString = loadHtmlPageUrl(url)

            var previewUrl = htmlString.substring(htmlString.indexOf("\"cover\":{\"url_list\":[\"\\/\\/"))
            previewUrl = previewUrl.substring(26, previewUrl.indexOf("\","))
            previewUrl = "http://" + previewUrl.filter { it != '\\' }
            Log.d("PreviewUrl", "Preview url: $previewUrl")

            handler.post {
                Glide.with(view)
                    .load(previewUrl)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            listener?.invoke(ModelImpl.ERROR_DOWNLOAD)
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            listener?.invoke(ModelImpl.SUCCESS_DOWNLOAD)
                            return false
                        }

                    })
                    .into(view)
            }
        }
    }

    override fun getVideoFiles() =
        storageApi.getFiles()

    override fun rate() {
        val appPackageName = context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (exception: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    override fun shareApp() {
        val packageName = context.packageName
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "${context.getString(R.string.share_text)} https://play.google.com/store/apps/details?id=$packageName"
        )
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }

    override fun shareVideo(path: String) {
        val intentShareFile = Intent(Intent.ACTION_SEND)
        val fileWithinMyDir = File(path)

        if (fileWithinMyDir.exists()) {
            intentShareFile.type = "video/*"
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$path"))

            intentShareFile.putExtra(
                Intent.EXTRA_SUBJECT,
                context.getString(R.string.share_video)
            )
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...")
            context.startActivity(Intent.createChooser(intentShareFile, "Share File"))
        }
    }

    override fun downloadVideo(url: String, listener: ((String) -> Unit)?) {
        tikTokApi.downloadVideo(url) {
            storageApi.writeFile(it) { videoPath ->
                listener?.invoke(videoPath)
            }
        }
    }

    override fun getCopyUrl(): String? {
        val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return if (service.hasPrimaryClip())
            service.primaryClip?.getItemAt(0)?.text.toString()
        else
            null
    }

    override fun openAppInMarket(packageName: String) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }

    private fun loadHtmlPageUrl(url: String): String {
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

        return urlString
    }
}