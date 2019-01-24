package com.yatochk.tiktokdownloader.view.download

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.utils.TIK_TOK_PACKAGE
import kotlinx.android.synthetic.main.fragment_download.*

class DownloadFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button_tiktok.setOnClickListener {
            val intent = activity?.packageManager?.getLaunchIntentForPackage(TIK_TOK_PACKAGE)
            if (intent != null)
                startActivity(intent)
            else
                Snackbar.make(it, getString(R.string.install_tik_tok), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.go_install)) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$TIK_TOK_PACKAGE")
                            )
                        )
                    }.show()
        }
    }
}