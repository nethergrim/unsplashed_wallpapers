package com.nethergrim.unsplashed.datasource

import android.util.Log
import com.firebase.client.*
import com.nethergrim.unsplashed.utils.toListOfWallpapers
import com.soikonomakis.rxfirebase.RxFirebase
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executors

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class FirebaseProvider private constructor() {

    private object Holder {
        val INSTANCE = FirebaseProvider()
    }

    companion object {
        val instance: FirebaseProvider by lazy { Holder.INSTANCE }
    }

    private val firebaseUrl: String by lazy { "https://unsplash-wallpapers.firebaseio.com/wallpapers" }
    private val firebase: Firebase by lazy { Firebase(firebaseUrl) }

    val data: HashMap<String, Wallpaper> = HashMap(9000)

    private val scheduler = Schedulers.newThread()


    fun getWallpapers(limit: Int = 10000): Observable<List<Wallpaper>> {
        Log.d("FirebaseProvider", "Loading $limit wallpapers")
        return RxFirebase.getInstance().observeValueEvent(Firebase(firebaseUrl).orderByPriority().limitToFirst(limit)).subscribeOn(scheduler).map({ it.toListOfWallpapers() })
    }

    fun getWallpaperById(id: String): Wallpaper? {
        return data.get(id)
    }

    fun incrementRating(id: String) {
        Executors.newSingleThreadExecutor().submit {
            firebase.child(id)
                    .runTransaction(object : Transaction.Handler {
                        override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                        }

                        override fun doTransaction(data: MutableData?): Transaction.Result? {
                            if (data == null) {
                                return Transaction.abort()
                            }
                            var priority = data.priority
                            if (priority == null) {
                                data.priority = 0
                                priority = 0
                            }
                            if (priority is Double) {
                                data.priority = priority - 1
                            }
                            return Transaction.success(data)
                        }
                    })
        }
    }

    fun decrementRating(id: String) {
        Executors.newSingleThreadExecutor().submit {
            firebase.child(id)
                    .runTransaction(object : Transaction.Handler {
                        override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                        }

                        override fun doTransaction(data: MutableData?): Transaction.Result? {
                            if (data == null) {
                                return Transaction.abort()
                            }

                            var priority = data.priority
                            if (priority == null) {
                                data.priority = 0
                                priority = 0
                            }
                            if (priority is Double) {
                                data.priority = priority + 1
                            }

                            return Transaction.success(data)
                        }
                    })
        }


    }

}