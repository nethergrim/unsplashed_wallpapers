package com.nethergrim.unsplashed.ui.adapters.viewHolders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

@Suppress("DEPRECATION")
/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class MainVh(val view: View) : RecyclerView.ViewHolder(view) {

    var imageView: ImageView? = null

    init {
        imageView = view as ImageView
    }

    companion object {
        fun generate(context: Context): MainVh {
            val draweeView = ImageView(context)
            draweeView.layoutParams = ViewGroup.LayoutParams(-1, -2)
            return MainVh(draweeView)
        }
    }

}