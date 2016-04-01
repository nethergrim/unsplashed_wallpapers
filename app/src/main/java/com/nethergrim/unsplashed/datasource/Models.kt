package com.nethergrim.unsplashed.datasource

import android.net.Uri

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

open class Wallpaper() {
    var id: String? = null
    var url: String? = null
}

val unsplashImageUrl: String = "https://images.unsplash.com/"

fun Wallpaper.previewUrl(): String {
    return unsplashImageUrl + id + "?w=500"

}

fun Wallpaper.previewUri(): Uri {
    return Uri.parse(unsplashImageUrl + id + "?w=500")

}

fun Wallpaper.fullSizeUrl(): String {
    return unsplashImageUrl + id
}

fun Wallpaper.fullSizeUri(): Uri {
    return Uri.parse(unsplashImageUrl + id)
}


