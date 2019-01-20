package com.yatochk.tiktokdownloader.view.galery

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yatochk.tiktokdownloader.R
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videos = ArrayList<Video>()
        for (i in 0..10) {
            videos.add(
                Video(
                    Uri.parse("https://metanit.com/java/android/11.1.php"),
                    Uri.parse("https://metanit.com/java/android/11.1.php")
                )
            )
        }

        recycler_video.layoutManager = GridLayoutManager(activity, 4)
        recycler_video.adapter = GalleryRecyclerAdapter(videos)
    }
}