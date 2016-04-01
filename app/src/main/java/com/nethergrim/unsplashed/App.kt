package com.nethergrim.unsplashed

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.firebase.client.Firebase

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
open class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Firebase.setAndroidContext(this)
        Fresco.initialize(this)
    }
}
