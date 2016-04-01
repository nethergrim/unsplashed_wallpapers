package com.nethergrim.unsplashed.datasource

import android.util.Log
import com.firebase.client.Firebase
import com.nethergrim.unsplashed.toListOfWallpapers
import com.soikonomakis.rxfirebase.RxFirebase
import rx.Observable
import rx.schedulers.Schedulers

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class FirebaseProvider private constructor() {

    init {

    }

    private object Holder {
        val INSTANCE = FirebaseProvider()
    }

    companion object {
        val instance: FirebaseProvider by lazy { Holder.INSTANCE }
    }

    private val firebaseUrl: String by lazy { "https://unsplash-wallpapers.firebaseio.com/wallpapers" }
    private val firebase: Firebase by lazy { Firebase(firebaseUrl) }

    fun getRandomizedWallpapers(): Observable<List<Wallpaper>> {

        RxFirebase.getInstance()
                .observeValueEvent(firebase)
                .subscribeOn(Schedulers.io())
                .map({it.toListOfWallpapers()})
                .doOnNext {
                    Log.e(TAG, it[0].fullSizeUrl())
                }
                .subscribe({})


        return Observable.empty()
    }


    val TAG = "FirebaseProvider"

}