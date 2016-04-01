package com.nethergrim.unsplashed

import android.support.v7.app.AppCompatActivity
import com.firebase.client.DataSnapshot
import com.nethergrim.unsplashed.datasource.Wallpaper
import java.util.*

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

fun AppCompatActivity.dp2px(dp: Int): Float {
    return dp * resources.displayMetrics.density
}


fun DataSnapshot.toListOfWallpapers(): List<Wallpaper> {
    val result = LinkedList<Wallpaper>()
    for (snapshot: DataSnapshot in this.children) {
        val wallpaper = snapshot.getValue(Wallpaper::class.java)
        result.add(wallpaper)
    }
    return result
}