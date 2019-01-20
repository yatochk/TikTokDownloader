package com.yatochk.tiktokdownloader.view.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.view.download.DownloadFragment
import com.yatochk.tiktokdownloader.view.galery.GalleryFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val adapter = MainPagerAdapter(
        supportFragmentManager,
        listOf(
            DownloadFragment(),
            GalleryFragment()
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.tab_download)))
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.tab_history)))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        main_pager.adapter = adapter
        main_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                main_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
    }

    override fun onBackPressed() {
        if (main_pager.currentItem == 1)
            main_pager.currentItem = 0
        else
            super.onBackPressed()
    }
}
