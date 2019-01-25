package com.yatochk.tiktokdownloader.view

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App

class RatingDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (activity != null) {
            val builder = AlertDialog.Builder(App.component.context)
            val layout = activity!!.layoutInflater.inflate(R.string.rating_dialog, null)

            ratingBar.setOnRatingBarChangeListener(RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                if (rating >= 4) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + activity!!.packageName)
                        )
                    )
                } else {
                    FeedbackDialog().show(fragmentManager, "Feedback")
                }
                activity!!.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putInt(Prefs.NEVER_PREF, 1)
                    .apply()
                dialog?.cancel()
            })
            val laterBtn = layout.findViewById(R.id.later_btn)
            laterBtn.setOnClickListener(View.OnClickListener {
                activity!!.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putInt(Prefs.LATER_COUNT_PREF, LATER_RATE_COUNT)
                    .apply()
                dialog?.cancel()
            })
            val neverBtn = layout.findViewById(R.id.never_btn)
            neverBtn.setOnClickListener(View.OnClickListener {
                activity!!.getSharedPreferences(Prefs.PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putInt(Prefs.NEVER_PREF, 1)
                    .apply()
                dialog?.cancel()
            })

            return builder.create()
        }

    }
}