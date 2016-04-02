package com.nethergrim.unsplashed.datasource

import android.net.Uri

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

open class Wallpaper(): Comparable<Wallpaper> {

    override fun compareTo(other: Wallpaper): Int {
        return rating.compareTo(other.rating) * -1
    }

    var id: String? = null
    var url: String? = null
    var rating: Int = 0
    var reversedRating: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Wallpaper

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Wallpaper(id=$id, url=$url, rating=$rating)"
    }

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


