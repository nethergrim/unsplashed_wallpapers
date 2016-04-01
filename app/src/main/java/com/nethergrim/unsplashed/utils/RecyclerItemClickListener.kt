package com.nethergrim.unsplashed.utils

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * *         All rights reserved.
 */

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class RecyclerItemClickListener(context: Context, protected var listener: RecyclerItemClickListener.OnItemClickListener) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    private var childView: View? = null

    private var childViewPosition: Int = 0

    init {
        this.gestureDetector = GestureDetector(context, GestureListener())
    }

    override fun onInterceptTouchEvent(view: RecyclerView, event: MotionEvent): Boolean {
        childView = view.findChildViewUnder(event.x, event.y)
        childViewPosition = view.getChildPosition(childView)
        gestureDetector.onTouchEvent(event)
        return false
    }

    override fun onTouchEvent(view: RecyclerView, event: MotionEvent) {
        // Not needed.
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    /**
     * A click listener for items.
     */
    interface OnItemClickListener {

        /**
         * Called when an item is clicked.

         * @param childView View of the item that was clicked.
         * *
         * @param position  Position of the item that was clicked.
         */
        fun onItemClick(childView: View, position: Int)

        /**
         * Called when an item is long pressed.

         * @param childView View of the item that was long pressed.
         * *
         * @param position  Position of the item that was long pressed.
         */
        fun onItemLongPress(childView: View, position: Int)

    }

    /**
     * A simple click listener whose methods can be overridden one by one.
     */
    abstract class SimpleOnItemClickListener : OnItemClickListener {

        /**
         * Called when an item is clicked. The default implementation is a no-op.

         * @param childView View of the item that was clicked.
         * *
         * @param position Position of the item that was clicked.
         */
        override fun onItemClick(childView: View, position: Int) {
            // Do nothing.
        }

        /**
         * Called when an item is long pressed. The default implementation is a no-op.

         * @param childView View of the item that was long pressed.
         * *
         * @param position Position of the item that was long pressed.
         */
        override fun onItemLongPress(childView: View, position: Int) {
            // Do nothing.
        }

    }

    protected inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            if (childView != null) {
                listener.onItemClick(childView as View, childViewPosition)
            }

            return true
        }

        override fun onLongPress(event: MotionEvent) {
            if (childView != null) {
                listener.onItemLongPress(childView as View, childViewPosition)
            }
        }

        override fun onDown(event: MotionEvent): Boolean {
            // Best practice to always return true here.
            // http://developer.android.com/training/gestures/detector.html#detect
            return true
        }

    }

}
