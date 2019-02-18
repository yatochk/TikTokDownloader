package com.downloaderfor.tiktok.view.galery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.dagger.App
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.File

class GalleryFragment : Fragment(), Gallery {

    private val presenter = App.component.galleryPresenter
    private var adapter = GalleryRecyclerAdapter {
        floating_button_delete.visibility =
                if (it) View.VISIBLE
                else View.INVISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adRequest = AdRequest.Builder().build()
        ads_gallery.loadAd(adRequest)

        presenter.bindView(this)
        recycler_video.layoutManager = GridLayoutManager(activity, 4)
        recycler_video.adapter = adapter
        floating_button_delete.setOnClickListener {
            adapter.deleteSelectedVideo()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden)
            presenter.unbindView()
        else
            presenter.bindView(this)
    }

    override fun updateVideos(files: ArrayList<File>) {
        adapter.updateVideos(files)
    }

    override fun onPause() {
        super.onPause()
        presenter.unbindView()
    }

    override fun onResume() {
        super.onResume()
        presenter.bindView(this)
    }
}