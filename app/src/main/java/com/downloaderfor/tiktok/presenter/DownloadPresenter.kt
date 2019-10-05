package com.downloaderfor.tiktok.presenter

import android.widget.ImageView
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.dagger.App
import com.downloaderfor.tiktok.model.Model
import com.downloaderfor.tiktok.model.ModelImpl
import com.downloaderfor.tiktok.utils.TIK_TOK_PACKAGE
import com.downloaderfor.tiktok.view.download.DownloaderView

class DownloadPresenter(private val model: Model) {
    companion object {
        private const val TIK_TOK_URL = "http://vm.tiktok.com"
    }

    private var view: DownloaderView? = null

    fun bindView(v: DownloaderView) {
        view = v
        val url = model.getCopyUrl()
        if (url != null && url != view?.url) {
            if (url.contains(TIK_TOK_URL))
                view?.url = url
            else
                view?.showToast(App.component.context.getString(R.string.not_tik_tok_url))
        }
    }

    fun unbindView() {
        view = null
    }

    fun clickPaste() {
        val url = model.getCopyUrl()
        if (url != null)
            view?.url = url
    }

    fun clickSnackAction() {
        model.openAppInMarket(TIK_TOK_PACKAGE)
    }

    fun clickClear() {
        view?.url = ""
    }

    private var lastVideoPath = ""
    fun clickDownload(url: String?) {
        if (url != null) {
            view?.showVideoLoad()
            model.downloadVideo(url) { videoPath, code ->
                when (code) {
                    ModelImpl.SUCCESS_DOWNLOAD -> {
                        App.isDownloaded = true
                        lastVideoPath = videoPath
                        view?.hideVideoLoad()
                    }

                    ModelImpl.ERROR_DOWNLOAD -> {
                        view?.showToast(App.component.context.getString(R.string.error_downloading))
                    }
                }
            }
        }
    }

    fun clickPreview() {
        if (lastVideoPath != "")
            view?.openVideo(lastVideoPath)
    }

    fun onDestroy() {
        model.dispose()
    }

    fun urlChange(url: String?, imageView: ImageView) {
        if (url != null)
            if (url.contains(TIK_TOK_URL)) {
                lastVideoPath = ""
                view?.showPreviewLoad()
                model.downloadPreview(url, imageView) {
                    if (it == ModelImpl.SUCCESS_DOWNLOAD)
                        view?.showPreview()
                    else {
                        view?.showToast(App.component.context.getString(R.string.error_downloading))
                        view?.showInstruction()
                    }
                }
            } else if (url.length > TIK_TOK_URL.length)
                view?.showToast(App.component.context.getString(R.string.not_tik_tok_url))
    }
}