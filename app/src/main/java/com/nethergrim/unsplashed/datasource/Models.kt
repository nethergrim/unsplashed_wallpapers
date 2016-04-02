package com.nethergrim.unsplashed.datasource

import android.net.Uri

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

open class Wallpaper() {
    var id: String? = null
    var url: String? = null
    var rating: Int? = null
}

val unsplashImageUrl: String = "https://images.unsplash.com/"
val previewQualifier = "?w=500"
val fullQualifier = "?w=3000"

fun Wallpaper.previewUrl(): String {
    return unsplashImageUrl + id + previewQualifier

}

fun Wallpaper.previewUri(): Uri {
    return Uri.parse(unsplashImageUrl + id + previewQualifier)

}

fun Wallpaper.fullSizeUrl(): String {
    return unsplashImageUrl + id + fullQualifier
}

fun Wallpaper.fullSizeUri(): Uri {
    return Uri.parse(unsplashImageUrl + id + fullQualifier)
}


