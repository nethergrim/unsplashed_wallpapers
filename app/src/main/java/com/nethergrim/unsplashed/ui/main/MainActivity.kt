package com.nethergrim.unsplashed.ui.main


import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.nethergrim.unsplashed.R
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.hide
import com.nethergrim.unsplashed.show
import com.nethergrim.unsplashed.ui.adapters.MainAdapter
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.onClick
import org.jetbrains.anko.progressBar
import org.jetbrains.anko.textView

class MainActivity : MvpActivity<MainView, MainViewPresenter>(), MainView {

    override fun showData(data: List<Wallpaper>) {
        // TODO add data to the adapter
        progressBar?.hide()
        errorView?.hide()

        val adapter = MainAdapter(data)
        recycler?.adapter = adapter
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
    var recycler: RecyclerView? = null
    var layoutManager: GridLayoutManager? = null

    private fun onRetryClicked() {
        presenter.startLoadingData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressBar = ProgressBar(this)
        recycler = RecyclerView(this@MainActivity)
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
        rootLayout?.addView(recycler, FrameLayout.LayoutParams(-1, -1))
        recycler?.setHasFixedSize(true)
        layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.main_screen_span_count))
        recycler?.layoutManager = layoutManager
        presenter.startLoadingData()
    }

    var spanCountDelta: Int = 0

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (layoutManager == null){
            return
        }
        if (spanCountDelta == 0 && newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // add one more span count
            spanCountDelta++
            layoutManager?.spanCount = layoutManager!!.spanCount + spanCountDelta
        } else if (spanCountDelta > 0 && newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager?.spanCount = layoutManager!!.spanCount - spanCountDelta
            spanCountDelta--
        }
    }
}
