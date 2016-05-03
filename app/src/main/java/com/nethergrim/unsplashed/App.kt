package com.nethergrim.unsplashed

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.facebook.drawee.backends.pipeline.Fresco
import com.firebase.client.Config
import com.firebase.client.Firebase
import com.google.android.gms.gcm.GcmNetworkManager
import com.google.android.gms.gcm.OneoffTask
import com.google.android.gms.gcm.PeriodicTask
import com.nethergrim.unsplashed.datasource.SyncService
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
        Fabric.with(this, Crashlytics(), Answers())
        Firebase.setAndroidContext(this)
        val config = Config()
        config.isPersistenceEnabled = true
        Firebase.setDefaultConfig(config)
        Fresco.initialize(this)

        val task = PeriodicTask.Builder()
                .setRequiredNetwork(OneoffTask.NETWORK_STATE_ANY)
                .setPersisted(true)
                .setRequiresCharging(true)
                .setService(SyncService::class.java)
                .setTag("sync")
                .setPeriod(60 * 12)
                .setFlex(60 * 6)
            .build()



        GcmNetworkManager.getInstance(this).schedule(task)
    }
}
