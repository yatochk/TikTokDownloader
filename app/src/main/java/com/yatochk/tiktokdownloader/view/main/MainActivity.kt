package com.yatochk.tiktokdownloader.view.main

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.view.download.DownloadFragment
import com.yatochk.tiktokdownloader.view.galery.GalleryFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainView {
    private val presenter = App.component.mainPresenter

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withActionBarDrawerToggleAnimated(true)
            .withHeader(R.layout.drow_header)
            .addDrawerItems(
                PrimaryDrawerItem()
                    .withName(R.string.drawer_downloader)
                    .withBadge("ad")
                    .withOnDrawerItemClickListener { _, _, _ ->
                        presenter.clickInstaDownloader()
                        true
                    },
                PrimaryDrawerItem()
                    .withName(R.string.drawer_rate)
                    .withOnDrawerItemClickListener { _, _, _ ->
                        presenter.clickRate()
                        true
                    },
                PrimaryDrawerItem()
                    .withName(R.string.drawer_recommend)
                    .withOnDrawerItemClickListener { _, _, _ ->
                        presenter.clickRecommend()
                        true
                    },
                PrimaryDrawerItem()
                    .withName(R.string.drawer_feedback)
                    .withOnDrawerItemClickListener { _, _, _ ->
                        presenter.clickFeedback()
                        true
                    },
                PrimaryDrawerItem()
                    .withName(R.string.drawer_privacy)
                    .withOnDrawerItemClickListener { _, _, _ ->
                        presenter.clickPrivacy()
                        true
                    }
            )
            .build()
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun onBackPressed() {
        if (main_pager.currentItem == 1)
            main_pager.currentItem = 0
        else
            super.onBackPressed()
    }

    override fun sendFeedback() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        val manager = applicationContext.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val version = info!!.versionName
        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(getString(R.string.developer_email)))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + version)
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "\n" + " Device :" + getDeviceName() +
                    "\n" + " System Version:" + Build.VERSION.SDK_INT +
                    "\n" + " Display Height  :" + height + "px" +
                    "\n" + " Display Width  :" + width + "px" +
                    "\n\n" + getString(R.string.have_problem) +
                    "\n"
        )
        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        return if (model.startsWith(manufacturer)) model
        else "$manufacturer $model"
    }

    override fun showPrivacy() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.privacy_title))
            .setMessage(R.string.privacy_message)
            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(R.drawable.ic_info)
            .show()
    }
}
