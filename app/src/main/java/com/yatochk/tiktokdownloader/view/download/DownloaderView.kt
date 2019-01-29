package com.yatochk.tiktokdownloader.view.download

interface DownloaderView {
    var url: String
    fun showToast(msg: String)
    fun showLoad()
    fun showVideo(path: String)
}