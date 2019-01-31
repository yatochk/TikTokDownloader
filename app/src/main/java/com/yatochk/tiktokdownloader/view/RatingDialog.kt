package com.yatochk.tiktokdownloader.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.LATER_COUNT_PREF
import com.yatochk.tiktokdownloader.utils.LATER_RATE_COUNT
import com.yatochk.tiktokdownloader.utils.NEVER_PREF
import com.yatochk.tiktokdownloader.utils.PREFS_NAME
import kotlinx.android.synthetic.main.rating_dialog.*


class RatingDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)
        val layout = activity!!.layoutInflater.inflate(R.layout.rating_dialog, null)
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
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
        later_btn.setOnClickListener {
            activity!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(LATER_COUNT_PREF, LATER_RATE_COUNT)
                .apply()
            dialog!!.cancel()
        }
        never_btn.setOnClickListener {
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