package com.yatochk.tiktokdownloader.view.download

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.material.snackbar.Snackbar
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.utils.TIK_TOK_PACKAGE
import kotlinx.android.synthetic.main.fragment_download.*


class DownloadFragment : Fragment(), DownloaderView {
    override var url: String
        get() = edit_url.editableText.toString()
        set(value) {
            edit_url.setText(value)
        }

    override fun showLoad() {
        download_instruction.visibility = View.INVISIBLE
        progress_download.visibility = View.VISIBLE
    }

    override fun showVideo(path: String) {
        progress_download.visibility = View.INVISIBLE
    }

    override fun showToast(msg: String) =
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()

    private val presenter = App.component.downloadPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adRequest = AdRequest.Builder().build()
        ads_download.loadAd(adRequest)

        button_tiktok.setOnClickListener {
            val intent = activity?.packageManager?.getLaunchIntentForPackage(TIK_TOK_PACKAGE)
            if (intent != null)
                startActivity(intent)
            else
                Snackbar.make(it, getString(R.string.install_tik_tok), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.go_install)) {
                        presenter.clickSnackAction()
                    }.show()
        }

        button_paste.setOnClickListener {
            presenter.clickPaste()
        }
        button_clear.setOnClickListener {
            presenter.clickClear()
        }

        edit_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.urlChange(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })


    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }
}