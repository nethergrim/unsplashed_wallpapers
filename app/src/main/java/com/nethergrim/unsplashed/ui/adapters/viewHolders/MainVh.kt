package com.nethergrim.unsplashed.ui.adapters.viewHolders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.nethergrim.unsplashed.R

@Suppress("DEPRECATION")
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
            draweeView.layoutParams = ViewGroup.LayoutParams(-1, -2)
            draweeView.aspectRatio = 1.5f

            val builder = GenericDraweeHierarchyBuilder(context.resources)
            builder.fadeDuration = 200
            builder.placeholderImage = context.resources.getDrawable(R.drawable.ic_image_black_24px)
            draweeView.hierarchy = builder.build()
            return MainVh(draweeView)
        }
    }

}