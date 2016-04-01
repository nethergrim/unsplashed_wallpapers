package com.nethergrim.unsplashed.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.hide
import com.nethergrim.unsplashed.show
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.onClick
import org.jetbrains.anko.progressBar
import org.jetbrains.anko.textView

class MainActivity : MvpActivity<MainView, MainViewPresenter>(), MainView {

    override fun showData(data: List<Wallpaper>) {
        // TODO add data to the adapter
        progressBar?.hide()
        errorView?.hide()
    }

    override fun showErrorView() {
        progressBar?.hide()
        errorView?.show()
    }


    override fun showLoadingView() {
        progressBar?.show()
        errorView?.hide()
    }


    override fun createPresenter(): MainViewPresenter {
        return MainViewPresenter()
    }

    var progressBar: ProgressBar? = null
    var rootLayout: FrameLayout? = null
    var errorView: TextView? = null

    private fun onRetryClicked(){
        presenter.startLoadingData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = ProgressBar(this)
        rootLayout = frameLayout {

            progressBar = progressBar {
                visibility = View.GONE
            }.lparams { gravity = Gravity.CENTER }

            errorView = textView {
                text = "Network error happened.\nTap to retry"
                gravity = Gravity.CENTER
                visibility = View.GONE
                onClick {
                    onRetryClicked()
                }
            }.lparams { gravity = Gravity.CENTER }

        }
        presenter.startLoadingData()
    }
}
