package com.yatochk.tiktokdownloader.view.galery

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.utils.URI_KEY
import com.yatochk.tiktokdownloader.view.preview.PreviewActivity
import kotlinx.android.synthetic.main.video_item.view.*
import java.io.File

class GalleryRecyclerAdapter(private val selectedListener: (Boolean) -> Unit) :
    ListAdapter<File, ViewHolder>(FileDiff()) {

    private val selectedItems: ArrayList<File> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(p0)

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.selected = false
        p0.bind(getItem(p1), object : ViewHolder.ItemClickListener {
            override fun longClick() {
                if (selectedItems.isEmpty())
                    selectedListener(true)

                with(p0) {
                    selected = !selected
                    if (selected)
                        selectedItems.add(getItem(p1))
                    else
                        selectedItems.remove(getItem(p1))
                }

                if (selectedItems.isEmpty())
                    selectedListener(false)
            }

            override fun click() {
                with(p0) {
                    if (selectedItems.isNotEmpty()) {
                        selected = !selected
                        if (selected)
                            selectedItems.add(getItem(p1))
                        else
                            selectedItems.remove(getItem(p1))
                    } else {
                        val intent = Intent(itemView.context, PreviewActivity::class.java)
                        intent.putExtra(URI_KEY, getItem(p1).absolutePath)
                        itemView.context.startActivity(intent)
                    }
                }

                if (selectedItems.isEmpty())
                    selectedListener(false)
            }
        })
    }

    fun deleteSelectedVideo() {
        selectedItems.forEach { selectedVideo ->
            App.component.model.deleteVideo(selectedVideo.absolutePath) {}
        }
        selectedItems.clear()
        submitList(App.component.model.getVideoFiles())
    }
}

class ViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
    ) {

    var selected = false
        set(value) {
            itemView.parent_video_item.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (value)
                        R.color.accent
                    else R.color.white
                )
            )
            field = value
        }

    fun bind(file: File, selectedListener: ItemClickListener) {
        with(itemView) {
            Glide.with(context)
                .load(file)
                .into(image_preview)

            setOnClickListener {
                selectedListener.click()
            }
            setOnLongClickListener {
                selectedListener.longClick()
                true
            }
        }
    }

    interface ItemClickListener {
        fun click()
        fun longClick()
    }
}

class FileDiff : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem.absolutePath == newItem.absolutePath
    }

    override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
        return oldItem == newItem
    }
}