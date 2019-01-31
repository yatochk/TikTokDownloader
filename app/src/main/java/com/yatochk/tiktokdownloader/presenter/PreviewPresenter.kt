package com.yatochk.tiktokdownloader.presenter

import com.yatochk.tiktokdownloader.model.Model
import com.yatochk.tiktokdownloader.view.preview.Preview

class PreviewPresenter(private val model: Model) {

    private var view: Preview? = null

    fun bindView(v: Preview) {
        view = v
    }

    fun unbindView() {
        view = null
    }

    fun clickShare(path: String) {

    }

    fun clickDelete(path: String) {

    }

    fun clickPreview() {
        view?.showVideo()
    }

}