package com.nethergrim.unsplashed.datasource

import com.firebase.client.Firebase
import rx.Observable

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

    private val firebaseUrl: String by lazy { "https://unsplash-wallpapers.firebaseio.com/" }
    private val firebase: Firebase by lazy { Firebase(firebaseUrl) }

    fun getRandomizedWallpapers() : Observable<List<Wallpaper>> {




        return Observable.empty()
    }

}