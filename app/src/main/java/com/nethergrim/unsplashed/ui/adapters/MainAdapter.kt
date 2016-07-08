package com.nethergrim.unsplashed.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.datasource.previewUrl
import com.nethergrim.unsplashed.ui.adapters.viewHolders.MainVh
import com.nethergrim.unsplashed.utils.PicassoImageLoader

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

class MainAdapter(var data: List<Wallpaper>, val callback: OnWallpaperClickListener) : RecyclerView.Adapter<MainVh>(), View.OnClickListener {


    override fun onBindViewHolder(p0: MainVh?, p1: Int) {
        val wallpaper = data[p1]
        if (p0?.imageView != null) {
            val imageView: ImageView = p0?.imageView as ImageView
            imageView.minimumHeight = (imageView.context.resources.displayMetrics.density * 140).toInt()
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            PicassoImageLoader.instance.loadImage(wallpaper.previewUrl(), imageView)
        }
        if (data.size > p1 + 3){
            PicassoImageLoader.instance.precacheImage(data[p1+3].previewUrl())
        }
        p0?.itemView?.setOnClickListener(this)
        p0?.itemView?.tag = p1
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): MainVh? {
        return MainVh.generate(p0?.context!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id?.hashCode()?.toLong() ?: 0
    }

    override fun onClick(v: View?) {
        val index = v?.tag ?: return
        val wallpaper = data[index as Int]
        callback.onWallpaperClicked(wallpaper)
    }

    interface OnWallpaperClickListener {
        fun onWallpaperClicked(wallpaper: Wallpaper);
    }
}
