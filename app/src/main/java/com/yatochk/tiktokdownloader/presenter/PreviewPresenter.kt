package com.yatochk.tiktokdownloader.presenter

import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
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