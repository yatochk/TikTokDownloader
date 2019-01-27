package com.yatochk.tiktokdownloader.presenter

import com.yatochk.tiktokdownloader.model.Model
import com.yatochk.tiktokdownloader.view.galery.Gallery

class GalleryPresenter(private val model: Model) {
    private var view: Gallery? = null

    fun bindView(v: Gallery) {
        view = v
        val videos = model.getVideoFiles()
        view?.updateVideos(videos)
    }

    fun unbindView() {
        view = null
    }
}