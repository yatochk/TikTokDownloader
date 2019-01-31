package com.yatochk.tiktokdownloader.view.galery

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.URI_KEY
import com.yatochk.tiktokdownloader.view.VideoActivity
import kotlinx.android.synthetic.main.video_item.view.*
import java.io.File

class GalleryRecyclerAdapter :
    ListAdapter<File, ViewHolder>(FileDiff()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.video_item, p0, false)
    )

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(getItem(p1))
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(file: File) {
        with(itemView) {
            Log.d("MYLOG", Uri.fromFile(file).toString())
            Glide.with(context)
                .load(file)
                .into(image_preview)

            image_preview.setOnClickListener {
                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra(URI_KEY, file.absolutePath)
                context.startActivity(intent)
            }
        }
    }
}

class FileDiff : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem.absolutePath == newItem.absolutePath
    }

    override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem.readBytes().contentEquals(newItem.readBytes())
    }
}