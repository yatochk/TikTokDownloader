package com.downloaderfor.tiktok.view

import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.utils.URI_KEY
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val adRequest = AdRequest.Builder().build()
        ads_video.loadAd(adRequest)

        val uri = intent.getStringExtra(URI_KEY)
        Log.d("VideoActivityUri", uri.toString())
        with(video_view) {
            setVideoPath(uri)
            setMediaController(MediaController(this@VideoActivity))
            requestFocus()
            start()
        }
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        video_view.holder.setFixedSize(size.x, size.y)
    }
}
