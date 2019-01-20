package com.yatochk.tiktokdownloader.view

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.URI_KEY
import kotlinx.android.synthetic.main.activity_video.*


class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        if (savedInstanceState != null) {
            val uri = Uri.parse(savedInstanceState.getString(URI_KEY))
            video_view.setVideoURI(uri)
            video_view.start()
        }
    }
}
