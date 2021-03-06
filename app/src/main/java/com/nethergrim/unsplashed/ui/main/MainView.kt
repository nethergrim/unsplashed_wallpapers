package com.nethergrim.unsplashed.ui.main

import com.hannesdorfmann.mosby.mvp.MvpView
import com.nethergrim.unsplashed.datasource.Wallpaper

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
interface MainView: MvpView {

    fun showLoadingView()

    fun showErrorView()

    fun setData(data: List<Wallpaper>)

}