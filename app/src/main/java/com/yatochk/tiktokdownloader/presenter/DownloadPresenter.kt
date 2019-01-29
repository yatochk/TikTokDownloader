package com.yatochk.tiktokdownloader.presenter

import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.model.Model
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

    fun urlChange(url: String?) {
        if (url != null)
            if (url.contains(TIK_TOK_URL)) {
                view?.showLoad()
                model.downloadVideo(url) {
                    view?.showVideo(it)
                }
            } else if (url.length > TIK_TOK_URL.length)
                view?.showToast(App.component.context.getString(R.string.not_tik_tok_url))
    }
}