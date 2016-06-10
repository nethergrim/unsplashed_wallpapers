package com.nethergrim.unsplashed

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.firebase.client.Config
import com.firebase.client.Firebase
import com.nethergrim.unsplashed.utils.PicassoImageLoader
import io.fabric.sdk.android.Fabric

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
open class App: Application() {

    companion object {
       lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (!BuildConfig.DEBUG){
            Fabric.with(this, Crashlytics(), Answers())
        }

        Firebase.setAndroidContext(this)
        val config = Config()
        config.isPersistenceEnabled = true
        Firebase.setDefaultConfig(config)


    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.e("APP", "Trim memory called with level: " + level)
        PicassoImageLoader.instance.clearMemoryCache()
        System.gc()
    }
}
