package com.nethergrim.unsplashed.ui.adapters.viewHolders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class MainVh(val view: View) : RecyclerView.ViewHolder(view) {

    var imageView: SimpleDraweeView? = null

    init {
        imageView = view as SimpleDraweeView
    }

    companion object {
        fun generate(context: Context): MainVh {
            var draweeView = SimpleDraweeView(context)
            draweeView.layoutParams = ViewGroup.LayoutParams(-1,-2)
            draweeView.aspectRatio = 1.5f
            return MainVh(draweeView)
        }
    }

}