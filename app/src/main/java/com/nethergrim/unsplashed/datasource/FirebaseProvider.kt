package com.nethergrim.unsplashed.datasource

import android.util.Log
import com.firebase.client.*
import com.nethergrim.unsplashed.utils.toListOfWallpapers
import com.soikonomakis.rxfirebase.RxFirebase
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*

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

    private val data: HashMap<String, Wallpaper> = HashMap(9000)

    fun getRandomizedWallpapers(): Observable<List<Wallpaper>> {
        val result = RxFirebase.getInstance()
                .observeValueEvent(firebase)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .first()
                .map({ it.toListOfWallpapers() })
                .doOnNext {
                    Log.e(TAG, "Size: ${it.size}}")
                    Log.e(TAG, it[0].fullSizeUrl())

                }
                .doOnNext { it -> Collections.shuffle(it) }
                .doOnNext {
                    Log.e(TAG, it[0].fullSizeUrl())
                    data.clear()
                    for (wallpaper in it) {
                        data.put(wallpaper.id ?: "", wallpaper)
                    }
                }
        return result
    }

    fun getWallpaperById(id: String): Wallpaper? {
        return data.get(id)
    }

    fun incrementRating(id: String) {
        firebase.child(id)
                .child("rating")
                .runTransaction(object : Transaction.Handler {
                    override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                    }

                    override fun doTransaction(data: MutableData?): Transaction.Result? {
                        if (data == null){
                            return Transaction.abort()
                        }
                        if (data.value == null) {
                            data.value = 1
                        } else {
                            var rating = data.getValue(Int::class.java)
                            data.value = ++rating
                        }
                        return Transaction.success(data)
                    }
                })
    }

    fun decrementRating(id: String) {
        firebase.child(id)
                .child("rating")
                .runTransaction(object : Transaction.Handler {
                    override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                    }

                    override fun doTransaction(data: MutableData?): Transaction.Result? {
                        if (data == null){
                            return Transaction.abort()
                        }
                        if (data.value == null) {
                            data.value = -1
                        } else {
                            var rating = data.getValue(Int::class.java)
                            data.value = --rating
                        }
                        return Transaction.success(data)
                    }
                })
    }


    val TAG = "FirebaseProvider"

}