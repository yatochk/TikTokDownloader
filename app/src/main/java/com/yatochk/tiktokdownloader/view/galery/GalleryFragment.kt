package com.yatochk.tiktokdownloader.view.galery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.File

class GalleryFragment : Fragment(), Gallery {

    private val presenter = App.component.galleryPresenter

    private val adapter = GalleryRecyclerAdapter {
        with(App.component.mainPresenter) {
            deleteFiles = if (it.isNotEmpty()) {

                it
            } else {
                emptySet()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.bindView(this)
        recycler_video.layoutManager = GridLayoutManager(activity, 4)
        recycler_video.adapter = adapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden)
            presenter.unbindView()
        else
            presenter.bindView(this)
    }

    override fun updateVideos(files: ArrayList<File>) {
        adapter.submitList(files)
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