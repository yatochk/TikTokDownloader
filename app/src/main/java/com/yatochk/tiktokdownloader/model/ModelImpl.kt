package com.yatochk.tiktokdownloader.model

import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.yatochk.tiktokdownloader.R

class ModelImpl(private val context: Context) : Model {
    override fun rate() {
        val appPackageName = context.packageName
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (exception: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    override fun shareApp() {
        val packageName = context.packageName
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            "${context.getString(R.string.share_text)} https://play.google.com/store/apps/details?id=$packageName"
        )
        sendIntent.type = "text/plain"
        context.startActivity(sendIntent)
    }

    override fun downloadVideo(url: String) {

    }

    override fun getCopyUrl(): String? {
        val service = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return if (service.hasPrimaryClip())
            service.primaryClip?.getItemAt(0)?.text.toString()
        else
            null
    }

    override fun openAppInMarket(packageName: String) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }


}