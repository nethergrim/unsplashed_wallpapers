package com.nethergrim.unsplashed.ui.details

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jaeger.library.StatusBarUtil
import com.nethergrim.unsplashed.R
import com.nethergrim.unsplashed.utils.dp2px
import com.nethergrim.unsplashed.utils.hide
import com.nethergrim.unsplashed.utils.show
import com.tbruyelle.rxpermissions.RxPermissions
import org.jetbrains.anko.*

@Suppress("NOTHING_TO_INLINE")
class DetailsActivity : MvpActivity<DetailsView, DetailsPresenter>(), DetailsView {


    lateinit var rootView: FrameLayout
    lateinit var progressBar: ProgressBar
    lateinit var errorText: TextView
    lateinit var imageView: SubsamplingScaleImageView
    lateinit var bottomLayout: LinearLayout
    lateinit var dialog: ProgressDialog


    companion object {
        val EXTRA_WALLPAPER_ID = "id"

        fun getIntent(context: Context, id: String): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_WALLPAPER_ID, id)
            return intent
        }
    }

    fun onRetryClicked() {
        presenter.loadPhoto()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(android.R.color.black)
        layout()

        RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({
                    val granted = it
                    if (granted) {
                        presenter.loadPhoto()
                    } else {
                        finish()
                    }
                })
        StatusBarUtil.setTransparent(this)
    }

    inline fun layout() {
        rootView = frameLayout {

            progressBar = progressBar {
                visibility = View.GONE
            }.lparams { gravity = Gravity.CENTER }

            errorText = textView {
                text = "Network error happened.\nTap to retry"
                gravity = Gravity.CENTER
                visibility = View.GONE
                textColor = Color.WHITE
                onClick {
                    onRetryClicked()
                }
            }.lparams { gravity = Gravity.CENTER }

            bottomLayout = linearLayout {
                weightSum = 5.0f
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                visibility = View.GONE

                imageButton {
                    imageResource = R.drawable.ic_share_black_24px
                    onClick { presenter.share() }
                }.lparams { weight = 1.0f; width = 0; height = -1 }

                imageButton {
                    imageResource = R.drawable.ic_file_download_black_24px
                    onClick { presenter.download() }
                }.lparams { weight = 1.0f; width = 0; height = -1 }

                imageButton {
                    imageResource = R.drawable.ic_wallpaper_black_24px
                    onClick { presenter.setToWallpaper() }
                }.lparams { weight = 1.0f; width = 0; height = -1 }

                imageButton {
                    imageResource = R.drawable.ic_thumb_down_black_24px
                    onClick { presenter.thumbsDown() }
                }.lparams { weight = 1.0f; width = 0; height = -1 }

                imageButton {
                    imageResource = R.drawable.ic_thumb_up_black_24px
                    onClick { presenter.thumbsUp() }
                }.lparams { weight = 1.0f; width = 0; height = -1 }

            }.lparams { width = -1; height = dp2px(48).toInt(); gravity = Gravity.BOTTOM }
        }
        imageView = SubsamplingScaleImageView(this)
        imageView.layoutParams = ViewGroup.LayoutParams(-1, -1)
        rootView.addView(imageView, 1)
    }


    override fun showLoadingView() {
        progressBar.show()
        errorText.hide()
        bottomLayout.hide()
    }

    override fun showContent(uri: Uri) {
        imageView.setImage(ImageSource.uri(uri))
        bottomLayout.show()
        progressBar.postDelayed({ progressBar.hide() }, 1500)
        errorText.hide()
    }

    override fun createPresenter(): DetailsPresenter {
        val id = intent.extras.getString(EXTRA_WALLPAPER_ID)
        return DetailsPresenter(id)
    }

    override fun showErrorView() {
        progressBar.hide()
        errorText.show()
        bottomLayout.hide()
    }

    override fun showMessage(message: String) {
        longToast(message)
    }

    override fun showBlockingProgress() {
        dialog = indeterminateProgressDialog(R.string.please_wait)
    }

    override fun hideBlockingProgress() {
        @Suppress("SENSELESS_COMPARISON")
        if (dialog != null) {
            dialog.dismiss()
        }
    }

}
