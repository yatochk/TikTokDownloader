package com.downloaderfor.tiktok.presenter

import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.dagger.App
import com.downloaderfor.tiktok.model.Model
import com.downloaderfor.tiktok.view.preview.Preview

class PreviewPresenter(private val model: Model) {

    private var view: Preview? = null

    fun bindView(v: Preview) {
        view = v
    }

    fun unbindView() {
        view = null
    }

    fun clickShare(path: String) {
        model.shareVideo(path)
    }

    fun clickDelete(path: String) {
        model.deleteVideo(path)
        App.component.galleryPresenter.update()
        view?.showToast(App.component.context.getString(R.string.video_deleted))
        view?.close()
    }

    fun clickPreview() {
        view?.showVideo()
    }

}