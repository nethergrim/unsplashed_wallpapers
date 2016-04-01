package com.nethergrim.unsplashed.ui.details

import android.net.Uri
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
interface DetailsView: MvpView {

    fun showLoadingView()

    fun showContent(uri: Uri)

    fun showErrorView()
}