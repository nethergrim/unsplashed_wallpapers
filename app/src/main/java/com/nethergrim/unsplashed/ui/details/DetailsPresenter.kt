package com.nethergrim.unsplashed.ui.details

import android.util.Log
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.nethergrim.unsplashed.datasource.FirebaseProvider
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.datasource.fullSizeUrl
import com.nethergrim.unsplashed.utils.getBitmapFromUrl
import com.nethergrim.unsplashed.utils.saveBitmapToDiskCacheAndRecycle
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@Suppress("NOTHING_TO_INLINE")
/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class DetailsPresenter(val id: String) : MvpBasePresenter<DetailsView>() {

    val TAG = "DetailsPresenter"

    inline fun loadPhoto() {
        if (isViewAttached){
            view?.showLoadingView()
        }

        Observable.just(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map({ FirebaseProvider.instance.getWallpaperById(id) ?: Wallpaper() })
                .map({ it.fullSizeUrl() })
                .map({ getBitmapFromUrl(it) })
                .map({ saveBitmapToDiskCacheAndRecycle(it) })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isViewAttached) {
                        view?.showContent(it)
                    }
                }, {
                    Log.e(TAG, "error", it)
                    if (isViewAttached) {
                        view?.showErrorView()
                    }
                });
    }

    fun share(){
        // TODO
    }

    fun download(){
        // TODO
    }

    fun setToWallpaper(){
        // TODO
    }
}