package com.yatochk.tiktokdownloader.view.galery

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.URI_KEY
import com.yatochk.tiktokdownloader.view.VideoActivity
import kotlinx.android.synthetic.main.video_item.view.*

class GalleryRecyclerAdapter(private var videos: ArrayList<Video>) :
    RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.video_item, p0, false)
    )

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        with(p0.itemView) {
            //image_preview.setImageURI(videos[p1].pathPreview)
            button_start_video.setOnClickListener {
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra(URI_KEY, videos[p1].pathVideo)
                context.startActivity(intent)
            }
        }

    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}

data class Video(
    val pathVideo: Uri,
    val pathPreview: Uri
)