package com.yatochk.tiktokdownloader.view.preview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.utils.URI_KEY
import com.yatochk.tiktokdownloader.view.VideoActivity
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : AppCompatActivity(), Preview {

    private lateinit var videoPath: String

    private val presenter = App.component.previewPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        videoPath = intent.getStringExtra(URI_KEY)

        Glide.with(this)
            .load(videoPath)
            .into(image_preview_activity)

        image_preview_activity.setOnClickListener {
            presenter.clickPreview()
        }

        button_delete.setOnClickListener {
            presenter.clickDelete(videoPath)
        }
        button_share.setOnClickListener {
            presenter.clickShare(videoPath)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unbindView()
    }

    override fun showToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    override fun showVideo() {
        val videoIntent = Intent(this, VideoActivity::class.java)
        videoIntent.putExtra(URI_KEY, videoPath)
        startActivity(videoIntent)
    }

    override fun close() =
        finish()
}
