package com.yatochk.tiktokdownloader.view.download

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.yatochk.tiktokdownloader.R
import com.yatochk.tiktokdownloader.dagger.App
import com.yatochk.tiktokdownloader.utils.TIK_TOK_PACKAGE
import com.yatochk.tiktokdownloader.utils.URI_KEY
import com.yatochk.tiktokdownloader.view.preview.PreviewActivity
import kotlinx.android.synthetic.main.download_preview.*
import kotlinx.android.synthetic.main.fragment_download.*


class DownloadFragment : Fragment(), DownloaderView {
    override fun hideVideoLoad() {
        progress_video_download.visibility = View.INVISIBLE
        button_download.text = getString(R.string.download_button)
    }

    override fun showVideoLoad() {
        button_download.text = ""
        progress_video_download.visibility = View.VISIBLE
    }

    override var url: String
        get() = edit_url.editableText.toString()
        set(value) {
            edit_url.setText(value)
        }

    override fun showPreviewLoad() {
        download_instruction.visibility = View.INVISIBLE
        download_preview.visibility = View.INVISIBLE
        progress_preview_download.visibility = View.VISIBLE
    }

    override fun showPreview() {
        download_instruction.visibility = View.INVISIBLE
        progress_preview_download.visibility = View.INVISIBLE
        download_preview.visibility = View.VISIBLE
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
            else {
                val parent = CustomSnackbar.findSuitableParent(view)
                val customSnackbar = CustomSnackbar.make(parent, CustomSnackbar.LENGTH_LONG)
                customSnackbar.setText(getString(R.string.install_tik_tok))
                customSnackbar.setAction(getString(R.string.go_install)) {
                    presenter.clickSnackAction()
                }
                customSnackbar.show()
            }
            /*Snackbar.make(it, getString(R.string.install_tik_tok), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.go_install)) {
                    presenter.clickSnackAction()
                }.show()*/
        }

        button_paste.setOnClickListener {
            presenter.clickPaste()
        }
        button_clear.setOnClickListener {
            presenter.clickClear()
        }

        edit_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.urlChange(s.toString(), image_download_preview)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })

        image_download_preview.setOnClickListener {
            presenter.clickPreview()
        }
        button_download.setOnClickListener {
            presenter.clickDownload(edit_url.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbindView()
    }

    override fun openVideo(videoPath: String) {
        val intent = Intent(App.component.context, PreviewActivity::class.java)
        intent.putExtra(URI_KEY, videoPath)
        startActivity(intent)
    }

    override fun showInstruction() {
        progress_preview_download.visibility = View.INVISIBLE
        download_preview.visibility = View.INVISIBLE
        download_instruction.visibility = View.VISIBLE
    }
}