package com.nethergrim.unsplashed.ui.adapters

import android.support.v7.widget.RecyclerView
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

class MainAdapter(var data: List<Wallpaper>) : RecyclerView.Adapter<MainVh>() {

    override fun onBindViewHolder(p0: MainVh?, p1: Int) {
        val wallpaper = data.get(p1)
        if (p0?.imageView != null) {
            val imageView: ImageView = p0?.imageView as ImageView
            PicassoImageLoader.instance.loadImage(wallpaper.previewUrl(), imageView, 200)
        }
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
}
