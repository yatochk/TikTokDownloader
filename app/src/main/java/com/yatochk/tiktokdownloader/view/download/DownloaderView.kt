package com.yatochk.tiktokdownloader.view.download

interface DownloaderView {
    var url: String
    fun showToast(msg: String)
    fun showPreviewLoad()
    fun showVideoLoad()
    fun hideVideoLoad()
    fun showPreview()
    fun openVideo(videoPath: String)
    fun showInstruction()
}