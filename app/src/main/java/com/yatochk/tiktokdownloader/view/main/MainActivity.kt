package com.yatochk.tiktokdownloader.view.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.view.download.DownloadFragment
import com.yatochk.tiktokdownloader.view.galery.GalleryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_pager.adapter = MainPagerAdapter(
            supportFragmentManager,
            listOf(
                DownloadFragment(),
                GalleryFragment()
            )
        )
    }
}
