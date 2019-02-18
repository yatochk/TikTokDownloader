package com.downloaderfor.tiktok.presenter

import com.downloaderfor.tiktok.model.Model
import com.downloaderfor.tiktok.view.galery.Gallery

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

    fun update() {
        view?.updateVideos(model.getVideoFiles())
    }
}