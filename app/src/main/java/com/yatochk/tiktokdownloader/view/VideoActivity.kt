package com.yatochk.tiktokdownloader.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.URI_KEY
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val uri = intent.getStringExtra(URI_KEY)
        Log.d("VideoActivityUri", uri.toString())
        video_view.setVideoPath(uri)
        video_view.start()
    }
}
