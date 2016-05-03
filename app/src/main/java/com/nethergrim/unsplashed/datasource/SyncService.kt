package com.nethergrim.unsplashed.datasource

import android.util.Log
import com.google.android.gms.gcm.GcmNetworkManager
import com.google.android.gms.gcm.GcmTaskService
import com.google.android.gms.gcm.TaskParams

/**
 * Created by andrej on 03.05.16.
 */
class SyncService: GcmTaskService() {



    override fun onRunTask(p0: TaskParams?): Int {

        FirebaseProvider.instance.getWallpapers().subscribe({
            Log.d("SyncService","Synced data")
        }, {
            Log.e("SyncService", "Error on syncing data", it)
        })


        return GcmNetworkManager.RESULT_SUCCESS
    }
}