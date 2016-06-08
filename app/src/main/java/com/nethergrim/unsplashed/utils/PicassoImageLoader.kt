package com.nethergrim.unsplashed.utils

import android.widget.ImageView
import com.nethergrim.unsplashed.App
import com.nethergrim.unsplashed.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoTools

/**
 * Created on 27.05.16.
 */
class PicassoImageLoader {

    private object Holder {
        val INSTANCE = PicassoImageLoader()
    }

    companion object {
        val instance: PicassoImageLoader by lazy { Holder.INSTANCE }
    }

    fun loadImage(url: String, imageView: ImageView){
        Picasso.with(App.instance).load(url).placeholder(R.drawable.thumbnail).into(imageView)
    }

    fun loadImage(url: String, imageView: ImageView, height: Int, width: Int){
        Picasso.with(App.instance).load(url).resize(width, height).onlyScaleDown().centerCrop().into(imageView)
    }

    fun precacheImage(url: String){
        Picasso.with(App.instance).load(url).fetch()
    }

    fun clearMemoryCache(){
        PicassoTools.clearCache(Picasso.with(App.instance))
    }
}