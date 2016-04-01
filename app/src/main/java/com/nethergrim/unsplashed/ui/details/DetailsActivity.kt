package com.nethergrim.unsplashed.ui.details

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.nethergrim.unsplashed.R
import com.nethergrim.unsplashed.utils.dp2px
import com.nethergrim.unsplashed.utils.hide
import com.nethergrim.unsplashed.utils.show
import com.tbruyelle.rxpermissions.RxPermissions
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
        rootView.addView(imageView, 1)


        val bottomLayout = LinearLayout(this)
        bottomLayout.setBackgroundColor(resources.getColor(R.color.colorAccent))
        bottomLayout.orientation = LinearLayout.HORIZONTAL
        bottomLayout.weightSum = 5f

        val bottomLayoutParams = FrameLayout.LayoutParams(-1, dp2px(48).toInt())
        bottomLayoutParams.gravity = Gravity.BOTTOM
        rootView.addView(bottomLayout, bottomLayoutParams)

        val btnShare = ImageButton(this)
        val shareParams = LinearLayout.LayoutParams(0, -1)
        shareParams.weight = 1f


    }

    fun fabClicked() {

    }

    override fun showLoadingView() {
        progressBar.show()
        errorText.hide()
    }

    override fun showContent(uri: Uri) {
        imageView.setImage(ImageSource.uri(uri))

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
    }

}
