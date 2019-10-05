package com.downloaderfor.tiktok.model

import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AndroidRuntimeException
import android.util.Log
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.downloaderfor.tiktok.BuildConfig
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.model.db.StorageApi
import com.downloaderfor.tiktok.model.download.TikTokApi
import com.downloaderfor.tiktok.utils.END_PREVIEW_URL
import com.downloaderfor.tiktok.utils.START_PREVIEW_URL
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ModelImpl(
    private val context: Context,
    private val tikTokApi: TikTokApi,
    private val storageApi: StorageApi
) : Model {

    companion object {
        const val SUCCESS_DOWNLOAD = 200
        const val ERROR_DOWNLOAD = 404

    }

    private val compositeDisposable = CompositeDisposable()

    override fun dispose() {
        compositeDisposable.dispose()
    }

    override fun deleteVideo(path: String) {
        storageApi.deleteFile(path)
    }

    override fun downloadPreview(url: String, view: ImageView, listener: ((code: Int) -> Unit)?) {
        compositeDisposable.add(Observable.just<Void>(null)
            .subscribeOn(Schedulers.io())
            .map {
                val htmlString = loadHtmlPageUrl(url)
                if (htmlString != "") {
                    var previewUrl = htmlString.substring(htmlString.indexOf(START_PREVIEW_URL))
                    previewUrl = previewUrl.substring(
                        START_PREVIEW_URL.length,
                        previewUrl.indexOf(END_PREVIEW_URL)
                    )
                    Log.d("PreviewUrl", "Preview url: $previewUrl")
                }
            }
            .switchMap {
                val loadSubject = PublishSubject.create<Int>()
                Glide.with(view)
                    .load(it)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            loadSubject.onError(IllegalArgumentException())
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            loadSubject.onNext(SUCCESS_DOWNLOAD)
                            return false
                        }

                    })
                    .into(view)
                loadSubject
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    listener?.invoke(SUCCESS_DOWNLOAD)
                },
                {
                    listener?.invoke(ERROR_DOWNLOAD)
                }
            )
        )
    }

    override fun getVideoFiles() =
        storageApi.getFiles()

    override fun rate() {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
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
        try {
            val intentShareFile = Intent(Intent.ACTION_SEND)
            val fileWithinMyDir = File(path)

            if (fileWithinMyDir.exists()) {
                intentShareFile.type = "video/*"

                val uri =
                    FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + ".provider",
                        File(path)
                    )

                intentShareFile.putExtra(Intent.EXTRA_STREAM, uri)
                intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intentShareFile.putExtra(
                    Intent.EXTRA_SUBJECT,
                    context.getString(R.string.share_video)
                )

                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...")
                context.startActivity(Intent.createChooser(intentShareFile, "Share File"))
            }
        } catch (e: AndroidRuntimeException) {
            //do nothing
        }
    }

    override fun downloadVideo(url: String, listener: ((String, Int) -> Unit)?) {
        try {
            tikTokApi.downloadVideo(url) {
                storageApi.writeFile(it) { videoPath ->
                    listener?.invoke(videoPath, SUCCESS_DOWNLOAD)
                }
            }
        } catch (e: Exception) {
            listener?.invoke("", ERROR_DOWNLOAD)
        }
    }

    override fun getCopyUrl(): String? {
        try {
            val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            return if (service.hasPrimaryClip())
                service.primaryClip?.getItemAt(0)?.text.toString()
            else
                null
        } catch (e: Exception) {
            //no implement
        }
        return null
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

            return urlString
        } catch (e: Exception) {
            Log.e("Download", "Download tiktok video page is failed")
        }

        return ""
    }
}