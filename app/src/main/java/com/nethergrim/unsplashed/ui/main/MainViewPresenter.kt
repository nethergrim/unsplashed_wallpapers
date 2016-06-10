package com.nethergrim.unsplashed.ui.main

import android.content.Context
import android.util.Log
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.nethergrim.unsplashed.datasource.FirebaseProvider
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.ui.details.DetailsActivity
import rx.android.schedulers.AndroidSchedulers

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class MainViewPresenter : MvpBasePresenter<MainView>() {

    val TAG = "MainViewPresenter"

    fun startLoadingData() {
        loadDataAndDisplay(100)
    }

    fun loadMoreData(limit: Int = 9000){
        loadDataAndDisplay(limit)
    }

    private fun loadDataAndDisplay(limit: Int){
        if (!isViewAttached) {
            return
        }
        view?.showLoadingView()

        FirebaseProvider.instance
                .getWallpapers(limit)
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil { !isViewAttached }
                .subscribe({
                    if (isViewAttached) {
                        view?.setData(it)
                    }
                }, {
                    Log.e(TAG, "Error", it)
                    if (isViewAttached) {
                        view?.showErrorView()
                    }
                })
    }

    fun openDetailsScreen(wallpaper: Wallpaper, context: Context) {
        context.startActivity(DetailsActivity.getIntent(context, wallpaper.id ?: ""))
    }

}