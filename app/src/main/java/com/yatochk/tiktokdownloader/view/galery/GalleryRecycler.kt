package com.yatochk.tiktokdownloader.view.galery

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import com.yatochk.tiktokdownloader.R

class GalleryRecycler(private var videos: ArrayList<Video>) : RecyclerView.Adapter<GalleryRecycler.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(
        inflate(p0.context, R.layout.video_item, p0)
    )

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)
}

data class Video(
    val name: String
)