package com.nethergrim.unsplashed

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.firebase.client.Config
import com.firebase.client.Firebase
import com.google.android.gms.gcm.GcmNetworkManager
import com.google.android.gms.gcm.OneoffTask
import com.google.android.gms.gcm.PeriodicTask
import com.nethergrim.unsplashed.datasource.SyncService
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

        val task = PeriodicTask.Builder()
                .setRequiredNetwork(OneoffTask.NETWORK_STATE_ANY)
                .setPersisted(true)
                .setRequiresCharging(true)
                .setService(SyncService::class.java)
                .setTag("sync")
                .setPeriod(3600 * 12)
                .setFlex(3600 * 6)
            .build()



        GcmNetworkManager.getInstance(this).schedule(task)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.e("APP", "Trim memory called with level: " + level)
        PicassoImageLoader.instance.clearMemoryCache()
    }
}
