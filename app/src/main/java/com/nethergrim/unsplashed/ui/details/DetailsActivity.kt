package com.nethergrim.unsplashed.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.nethergrim.unsplashed.utils.hide
import com.nethergrim.unsplashed.utils.show
import org.jetbrains.anko.*

class DetailsActivity : MvpActivity<DetailsView, DetailsPresenter>(), DetailsView {


    lateinit var rootView: FrameLayout
    lateinit var progressBar: ProgressBar
    lateinit var errorText: TextView
    lateinit var imageView: SubsamplingScaleImageView

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
        presenter.loadPhoto()
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
        }
        imageView = SubsamplingScaleImageView(this)
        imageView.layoutParams = ViewGroup.LayoutParams(-1, -1)
        rootView.addView(imageView, 0)
    }

    override fun showLoadingView() {
        progressBar.show()
        errorText.hide()
    }

    override fun showContent(uri: Uri) {
        imageView.setImage(ImageSource.uri(uri))

        progressBar.hide()
        errorText.hide()
    }

    override fun createPresenter(): DetailsPresenter {
        val id = intent.extras.getString(EXTRA_WALLPAPER_ID)
        return DetailsPresenter(id)
    }

    override fun showErrorView() {
        progressBar.hide()
        errorText.show()
    }

}
