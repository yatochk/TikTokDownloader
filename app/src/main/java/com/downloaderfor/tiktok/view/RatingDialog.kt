package com.downloaderfor.tiktok.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.utils.*
import de.cketti.mailto.EmailIntentBuilder
import kotlinx.android.synthetic.main.rating_dialog.view.*
import java.util.*


class RatingDialog : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rating_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val layout = activity!!.layoutInflater.inflate(R.layout.rating_dialog, null)

        layout.rating_bar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            if (rating >= 4) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + activity!!.packageName)
                    )
                )
            } else {
                val metrics = DisplayMetrics()
                activity!!.windowManager.defaultDisplay.getMetrics(metrics)
                val height = metrics.heightPixels
                val width = metrics.widthPixels
                val manager = activity!!.applicationContext.packageManager
                var info: PackageInfo? = null
                try {
                    info = manager.getPackageInfo(activity!!.packageName, 0)
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }

                val version = info!!.versionName
                val language = Locale.getDefault().displayLanguage
                EmailIntentBuilder.from(activity!!)
                    .to(getString(R.string.developer_email))
                    .subject(getString(R.string.app_name))
                    .body(
                        "\n" + " Device :" + getDeviceName() +
                                "\n" + " System Version: " + Build.VERSION.SDK_INT +
                                "\n" + " Display Height: " + height + "px" +
                                "\n" + " Display Width: " + width + "px" +
                                "\n" + " App version: " + version +
                                "\n" + " System language: " + language +
                                "\n\n" + getString(R.string.have_problem) +
                                "\n"
                    )
                    .start()
            }
            activity!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(NEVER_PREF, 1)
                .apply()
            dialog!!.cancel()
        }
        layout.rating_later_btn.setOnClickListener {
            activity!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(LATER_COUNT_PREF, LATER_RATE_COUNT)
                .apply()
            dialog!!.cancel()
        }
        layout.rating_never_btn.setOnClickListener {
            activity!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(NEVER_PREF, 1)
                .apply()
            dialog!!.cancel()
        }
        builder.setView(layout)
        return builder.create()
    }
}