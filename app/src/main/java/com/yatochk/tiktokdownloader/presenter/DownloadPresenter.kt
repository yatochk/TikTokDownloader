package com.yatochk.tiktokdownloader.presenter

import android.widget.ImageView
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.model.Model
import com.yatochk.tiktokdownloader.model.ModelImpl
import com.yatochk.tiktokdownloader.utils.TIK_TOK_PACKAGE
import com.yatochk.tiktokdownloader.view.download.DownloaderView

class DownloadPresenter(private val model: Model) {
    companion object {
        private const val TIK_TOK_URL = "http://vm.tiktok.com"
    }

    private var view: DownloaderView? = null

    fun bindView(v: DownloaderView) {
        view = v
        val url = model.getCopyUrl()
        if (url != null && url != view?.url)
            view?.url = url
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
            model.downloadVideo(url) {
                App.isDownloaded = true
                lastVideoPath = it
                view?.hideVideoLoad()
            }
        }
    }

    fun clickPreview() {
        if (lastVideoPath != "")
            view?.openVideo(lastVideoPath)
    }

    fun urlChange(url: String?, imageView: ImageView) {
        if (url != null)
            if (url.contains(TIK_TOK_URL)) {
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