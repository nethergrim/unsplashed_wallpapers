@file:Suppress("NOTHING_TO_INLINE")

package com.nethergrim.unsplashed.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.firebase.client.DataSnapshot
import com.nethergrim.unsplashed.App
import com.nethergrim.unsplashed.datasource.Wallpaper
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
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

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

inline fun getBitmapFromUrl(url: String): Bitmap {
    val connection = java.net.URL(url).openConnection()
    connection.doInput = true
    connection.connect()
    val bitmap = BitmapFactory.decodeStream(connection.inputStream)
    return bitmap
}

inline fun getInputStreamFromUrl(url: String): InputStream {
    return URL(url).openStream()
}

inline fun saveBitmapToDiskCacheAndRecycle(bitmap: Bitmap): Uri {
    var file = App.instance.cacheDir
    file = File(file, "unsplashed_" + System.currentTimeMillis() + ".jpg")
    file.createNewFile()
    val out = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    out.flush()
    out.close()
    bitmap.recycle()
    return Uri.fromFile(file)
}

inline fun saveBitmapToCache(url: String): Uri {
    val context = App.instance
    return FileUtils.downloadImagesToCache(url, context)
}

inline fun saveBitmapToDownloads(url: String, name: String): Uri {
    val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name + ".jpg")
    return FileUtils.downloadImage(url, file)
}