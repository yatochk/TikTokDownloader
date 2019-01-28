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
    }

    fun unbindView() {
        view = null
    }

    fun clickPaste() {
        val text = model.getCopyUrl()
        if (text != null)
            view?.setUrl(text)
    }

    fun clickSnackAction() {
        model.openAppInMarket(TIK_TOK_PACKAGE)
    }

    fun clickClear() {
        view?.setUrl("")
    }

    fun urlChange(url: String?) {
        model.downloadVideo(url!!)

        if (url != null)
            if (url.contains(TIK_TOK_URL))
                model.downloadVideo(url)
            else if (url.length > TIK_TOK_URL.length)
                view?.showToast(App.component.context.getString(R.string.not_tik_tok_url))
    }
}