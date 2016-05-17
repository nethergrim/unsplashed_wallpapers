package com.nethergrim.unsplashed.datasource

import com.google.android.gms.gcm.GcmNetworkManager
import com.google.android.gms.gcm.GcmTaskService
import com.google.android.gms.gcm.TaskParams

/**
 * Created by andrej on 03.05.16.
 */
class SyncService: GcmTaskService() {



    override fun onRunTask(p0: TaskParams?): Int {
        FirebaseProvider
                .instance
                .getWallpapers()
                .toBlocking()
                .first()
        return GcmNetworkManager.RESULT_SUCCESS
    }
}