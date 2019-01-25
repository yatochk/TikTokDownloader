package com.yatochk.tiktokdownloader.presenter

import com.yatochk.tiktokdownloader.model.Model
import com.yatochk.tiktokdownloader.utils.INSTAGRAM_DOWNLOADER_PACKAGE
import com.yatochk.tiktokdownloader.view.main.MainView

class MainPresenter(private val model: Model) {
    private var view: MainView? = null

    fun bindView(v: MainView) {
        view = v
    }

    fun unbindView() {
        view = null
    }

    fun clickInstaDownloader() =
        model.openAppInMarket(INSTAGRAM_DOWNLOADER_PACKAGE)

    fun clickRate() {
        model.rate()
    }

    fun clickRecommend() =
        model.shareApp()

    fun clickFeedback() =
        view?.sendFeedback()

    fun clickPrivacy() =
        view?.showPrivacy()
}