package com.nethergrim.unsplashed.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.nethergrim.unsplashed.datasource.Wallpaper
import com.nethergrim.unsplashed.datasource.previewUri
import com.nethergrim.unsplashed.ui.adapters.viewHolders.MainVh

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */

class MainAdapter(val data: List<Wallpaper>) : RecyclerView.Adapter<MainVh>() {


    override fun onBindViewHolder(p0: MainVh?, p1: Int) {
        val wallpaper = data.get(p1)
        if (p0?.imageView != null) {
            val imageView: SimpleDraweeView = p0?.imageView as SimpleDraweeView
            imageView.setImageURI(wallpaper.previewUri(), wallpaper)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): MainVh? {
        return MainVh.generate(p0?.context!!)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
