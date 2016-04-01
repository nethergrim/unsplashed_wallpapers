package com.nethergrim.unsplashed

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

data class Wallpaper(val id: String, val url: String)

val unsplashImageUrl: String = "https://images.unsplash.com/"

fun Wallpaper.previewUrl(): String {
    return unsplashImageUrl + id  + "?w=500"

}

fun Wallpaper.fullSizeUrl(): String {
    return unsplashImageUrl + id
}


