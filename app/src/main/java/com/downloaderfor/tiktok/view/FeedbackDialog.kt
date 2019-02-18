package com.downloaderfor.tiktok.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.DialogFragment
import com.downloaderfor.tiktok.R
import com.downloaderfor.tiktok.utils.getDeviceName
import kotlinx.android.synthetic.main.feedback_dialog.view.*


class FeedbackDialog : DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val layout = activity!!.layoutInflater.inflate(R.layout.feedback_dialog, null)
        layout.send_btn.setOnClickListener {
            val displayMetrics = DisplayMetrics()
            activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            val manager = activity!!.applicationContext.packageManager
            var info: PackageInfo? = null
            try {
                info = manager.getPackageInfo(activity!!.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            var version: String? = null
            if (info != null) {
                version = info.versionName
            }
            val i = Intent(Intent.ACTION_SEND)
            i.type = "message/rfc822"
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(resources.getString(R.string.developer_email)))
            i.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name) + version!!)
            i.putExtra(
                Intent.EXTRA_TEXT,
                "\n" + " Device :" + getDeviceName() +
                        "\n" + " System Version:" + Build.VERSION.SDK_INT +
                        "\n" + " Display Height  :" + height + "px" +
                        "\n" + " Display Width  :" + width + "px" +
                        "\n\n" + layout.feed_text.text.toString() +
                        "\n"
            )
            startActivity(Intent.createChooser(i, "Send Email"))
        }
        layout.close_btn.setOnClickListener { dialog?.cancel() }
        builder.setView(layout)
        return builder.create()
    }
}