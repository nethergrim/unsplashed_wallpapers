package com.nethergrim.unsplashed.ui.main


import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.jaeger.library.StatusBarUtil
import com.nethergrim.unsplashed.R
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.ui.adapters.MainAdapter
import com.nethergrim.unsplashed.utils.RecyclerItemClickListener
import com.nethergrim.unsplashed.utils.dp2px
import com.nethergrim.unsplashed.utils.hide
import com.nethergrim.unsplashed.utils.show
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.onClick
import org.jetbrains.anko.progressBar
import org.jetbrains.anko.textView
import java.util.*

class MainActivity : MvpActivity<MainView, MainViewPresenter>(), MainView {


    var progressBar: ProgressBar? = null
    var rootLayout: FrameLayout? = null
    var errorView: TextView? = null
    var recycler: RecyclerView? = null
    var layoutManager: GridLayoutManager? = null
    var spanCountDelta: Int = 0
    var adapter: MainAdapter? = null
    var horizontalDecoration: RecyclerView.ItemDecoration? = null
    var removedHorizontalDecoration = false
    var loadingData = false
    var lastCount = -1

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
        recycler?.layoutParams = FrameLayout.LayoutParams(-1, -1)
        rootLayout?.addView(recycler, 1)
        recycler?.setHasFixedSize(true)
        recycler?.addOnItemTouchListener(RecyclerItemClickListener(this, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(childView: View, position: Int) {
                if (adapter == null) {
                    return
                }
                if (adapter!!.data.size > position && position >= 0) {
                    val wallpaper = adapter!!.data[position]
                    presenter.openDetailsScreen(wallpaper, this@MainActivity)
                }
            }

            override fun onItemLongPress(childView: View, position: Int) {
            }
        }))
        layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.main_screen_span_count))
        recycler?.layoutManager = layoutManager





        horizontalDecoration = VerticalDividerItemDecoration.Builder(this)
                .color(Color.BLACK)
                .size(dp2px(2).toInt())
                .build()
        if (layoutManager!!.spanCount > 1) {
            recycler?.addItemDecoration(horizontalDecoration)
        }

        recycler?.addItemDecoration(HorizontalDividerItemDecoration.Builder(this)
                .color(Color.BLACK)
                .size(dp2px(2).toInt())
                .build())


        Log.d("MainActivity", "starting loading data")
        presenter.startLoadingData()
        adapter = MainAdapter(LinkedList())
        recycler?.adapter = adapter

        StatusBarUtil.setTransparent(this)
        rootLayout?.fitsSystemWindows = false


    }


    override fun setData(data: List<Wallpaper>) {
        Log.d("MainActivity", "loaded wallpapers: " + data.size)
        if (data.size - lastCount > 250) {
            loadingData = false
        }
        adapter?.data = data
        adapter?.notifyDataSetChanged()
        progressBar?.postDelayed({ progressBar?.hide() }, 2000)
        errorView?.hide()
        if (adapter!!.itemCount < 6000 && !loadingData){
            presenter.loadMoreData(adapter!!.itemCount + 300)
            loadingData = true
        }
        lastCount = data.size
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (layoutManager == null) {
            return
        }
        if (spanCountDelta == 0 && newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // add one more span count
            spanCountDelta++
            layoutManager?.spanCount = layoutManager!!.spanCount + spanCountDelta
            if (removedHorizontalDecoration && layoutManager!!.spanCount > 1) {
                removedHorizontalDecoration = false
                recycler?.addItemDecoration(horizontalDecoration)
            }
        } else if (spanCountDelta > 0 && newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager?.spanCount = layoutManager!!.spanCount - spanCountDelta
            spanCountDelta--
            if (layoutManager?.spanCount == 1) {
                removedHorizontalDecoration = true
                recycler?.removeItemDecoration(horizontalDecoration)
            }
        }
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
}
