package com.yatochk.tiktokdownloader.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.DialogFragment
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.LATER_COUNT_PREF
import com.yatochk.tiktokdownloader.utils.LATER_RATE_COUNT
import com.yatochk.tiktokdownloader.utils.NEVER_PREF
import com.yatochk.tiktokdownloader.utils.PREFS_NAME
import kotlinx.android.synthetic.main.rating_dialog.view.*


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
                FeedbackDialog().show(fragmentManager!!, "Feedback")
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