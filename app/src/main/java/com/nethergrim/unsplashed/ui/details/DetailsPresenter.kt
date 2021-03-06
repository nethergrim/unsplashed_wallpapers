package com.nethergrim.unsplashed.ui.details

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.nethergrim.unsplashed.App
import com.nethergrim.unsplashed.datasource.FirebaseProvider
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.datasource.fullSizeUrl
import com.nethergrim.unsplashed.utils.WallpaperTransformation
import com.nethergrim.unsplashed.utils.saveBitmapToCache
import com.nethergrim.unsplashed.utils.saveBitmapToDownloads
import com.squareup.picasso.Picasso
import com.yandex.metrica.YandexMetrica
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
    var updatedRating = false

    inline fun loadPhoto() {
        if (isViewAttached) {
            view?.showLoadingView()
        }
        Observable.just(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map({ FirebaseProvider.instance.getWallpaperById(id) ?: Wallpaper() })
                .map({ saveBitmapToCache(it.fullSizeUrl(), it.id!!) })
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

    fun share(context: Context) {
        if (isViewAttached) {
            view?.showBlockingProgress()
        }
        Observable.just(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map({ FirebaseProvider.instance.getWallpaperById(id) ?: Wallpaper() })
                .map({ saveBitmapToDownloads(it.fullSizeUrl(), it.id!!) })
                .doOnNext { YandexMetrica.reportEvent("sharing wallpaper ", "$id") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isViewAttached) {
                        view?.hideBlockingProgress()
                        val shareIntent = Intent();
                        shareIntent.action = Intent.ACTION_SEND;
                        shareIntent.putExtra(Intent.EXTRA_STREAM, it);
                        shareIntent.type = "image/jpeg";
                        context.startActivity(Intent.createChooser(shareIntent, "Share to:"))
                    }
                }, {
                    Log.e(TAG, "error", it)
                    if (isViewAttached) {
                        view?.hideBlockingProgress()
                        view?.showErrorView()
                    }
                })
    }

    fun download() {
        if (isViewAttached) {
            view?.showBlockingProgress()
        }
        Observable.just(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map({ FirebaseProvider.instance.getWallpaperById(id) ?: Wallpaper() })
                .map { saveBitmapToDownloads(it.fullSizeUrl(), it.id ?: System.currentTimeMillis().toString()) }
                .doOnNext { YandexMetrica.reportEvent("downloading wallpaper ", "$id") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isViewAttached) {
                        view?.hideBlockingProgress()
                        view?.showMessage("Downloaded image to:\n" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                    }
                }, {
                    Log.e(TAG, "error", it)
                    if (isViewAttached) {
                        view?.hideBlockingProgress()
                        view?.showErrorView()
                    }
                })
    }

    fun setToWallpaper() {
        if (isViewAttached) {
            view?.showBlockingProgress()
        }
        Observable.just(id)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map({ FirebaseProvider.instance.getWallpaperById(id) ?: Wallpaper() })
                .map { it.fullSizeUrl() }
                .doOnNext { System.gc() }
                .map { Picasso.with(App.instance).load(it).config(Bitmap.Config.ARGB_8888).transform(WallpaperTransformation()).get() }
                .doOnNext {
                    val wallpapersManager = WallpaperManager.getInstance(App.instance)
                    wallpapersManager.setBitmap(it)
                }
                .doOnNext { YandexMetrica.reportEvent("setting wallpaper ", "$id") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isViewAttached) {
                        view?.hideBlockingProgress()
                        view?.showMessage("Success")
                    }
                }, {
                    Log.e(TAG, "error", it)
                    if (isViewAttached) {
                        view?.hideBlockingProgress()
                        view?.showMessage("Error happened. Please try again.")
                    }
                })
    }

    fun thumbsUp() {
        if (updatedRating)
            return
        if (isViewAttached) {
            updatedRating = true
            view?.showMessage("Thank you!")
            FirebaseProvider.instance.incrementRating(id)
        }
    }

    fun thumbsDown() {
        if (updatedRating)
            return
        if (isViewAttached) {
            updatedRating = true
            view?.showMessage("Thank you!")
            FirebaseProvider.instance.decrementRating(id)
        }
    }
}