package com.yatochk.tiktokdownloader.view.galery

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.utils.URI_KEY
import com.yatochk.tiktokdownloader.view.preview.PreviewActivity
import kotlinx.android.synthetic.main.video_item.view.*
import java.io.File

class GalleryRecyclerAdapter(private val selectedListener: (Boolean) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    private val selectedItems: ArrayList<File> = ArrayList()

    private val items = ArrayList<File>()
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder = ViewHolder(p0)

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.selected = false
        p0.bind(items[p1], object : ViewHolder.ItemClickListener {
            override fun longClick() {
                with(p0) {
                    selected = !selected
                    if (selected)
                        selectedItems.add(items[p1])
                    else
                        selectedItems.remove(items[p1])
                }
                selectedListener(selectedItems.isNotEmpty())
            }

            override fun click() {
                with(p0) {
                    if (selectedItems.isNotEmpty()) {
                        selected = !selected
                        if (selected)
                            selectedItems.add(items[p1])
                        else
                            selectedItems.remove(items[p1])
                    } else {
                        val intent = Intent(itemView.context, PreviewActivity::class.java)
                        intent.putExtra(URI_KEY, items[p1].absolutePath)
                        itemView.context.startActivity(intent)
                    }
                }

                if (selectedItems.isEmpty())
                    selectedListener(false)
            }
        })
    }

    fun deleteSelectedVideo() {
        while (selectedItems.isNotEmpty()) {
            App.component.model.deleteVideo(selectedItems[0].absolutePath)
            selectedItems.removeAt(0)
        }
        selectedListener(false)
        updateVideos(App.component.model.getVideoFiles())
    }

    fun updateVideos(videos: List<File>) {
        items.clear()
        items.addAll(videos)
        notifyDataSetChanged()
    }
}

class ViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
    ) {

    var selected = false
        set(value) {
            itemView.item_view_dark.visibility = if (value) View.VISIBLE else View.INVISIBLE
            itemView.item_view_check.visibility = if (value) View.VISIBLE else View.INVISIBLE
            field = value
        }

    fun bind(file: File, selectedListener: ItemClickListener) {
        with(itemView) {
            Glide.with(context)
                .load(file)
                .into(item_view)

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