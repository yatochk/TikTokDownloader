package com.downloaderfor.tiktok.presenter

import com.downloaderfor.tiktok.model.Model
import com.downloaderfor.tiktok.view.main.MainView

class MainPresenter(private val model: Model) {
    private var view: MainView? = null

    fun bindView(v: MainView) {
        view = v
    }

    fun unbindView() {
        view = null
    }

    fun clickRate() {
        model.rate()
    }

    fun clickRecommend() =
        model.shareApp()

    fun clickFeedback() =
        view?.sendFeedback()

    fun clickPrivacy() =
        view?.showPrivacy()

    fun downloadComplete() =
        view?.goToHistory()
}