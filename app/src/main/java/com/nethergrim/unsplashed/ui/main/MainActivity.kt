package com.nethergrim.unsplashed.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import com.hannesdorfmann.mosby.mvp.MvpActivity
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.progressBar

class MainActivity : MvpActivity<MainView, MainViewPresenter>(), MainView {


    override fun showLoadingView() {
        progressBar?.visibility = View.VISIBLE
    }


    override fun createPresenter(): MainViewPresenter {
        return MainViewPresenter()
    }

    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = ProgressBar(this)
        val layout = frameLayout {


            progressBar = progressBar {
                visibility = View.GONE
            }.lparams { gravity = Gravity.CENTER }

        }
        presenter.startLoadingData()
    }
}
