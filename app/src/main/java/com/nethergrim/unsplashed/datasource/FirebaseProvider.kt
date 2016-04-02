package com.nethergrim.unsplashed.datasource

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
    private val dataSet: Set<Wallpaper> = Collections.synchronizedSet<Wallpaper>(HashSet<Wallpaper>())
    private val scheduler = Schedulers.newThread()

    fun getWallpapers(): Observable<LinkedList<Wallpaper>> {


        val full = RxFirebase.getInstance()
                .observeValueEvent(firebase.orderByChild("reversedRating"))
                .subscribeOn(scheduler)
                .observeOn(scheduler)


        val result = full
                .subscribeOn(scheduler)
                .observeOn(scheduler)
                .map({ it.toListOfWallpapers() })
                .doOnNext {
                    it.forEach { data.put(it.id ?: "", it); dataSet.plus(it) }
                }
                .map { LinkedList<Wallpaper>(it) }
                .doOnNext { Collections.sort(it) }
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
                        if (data == null) {
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

        firebase.child(id)
                .child("reversedRating")
                .runTransaction(object : Transaction.Handler {
                    override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                    }

                    override fun doTransaction(data: MutableData?): Transaction.Result? {
                        if (data == null) {
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

    fun decrementRating(id: String) {
        firebase.child(id)
                .child("rating")
                .runTransaction(object : Transaction.Handler {
                    override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                    }

                    override fun doTransaction(data: MutableData?): Transaction.Result? {
                        if (data == null) {
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

        firebase.child(id)
                .child("reversedRating")
                .runTransaction(object : Transaction.Handler {
                    override fun onComplete(p0: FirebaseError?, p1: Boolean, p2: DataSnapshot?) {
                    }

                    override fun doTransaction(data: MutableData?): Transaction.Result? {
                        if (data == null) {
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


    val TAG = "FirebaseProvider"

}