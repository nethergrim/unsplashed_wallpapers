package com.nethergrim.unsplashed.ui.main

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.nethergrim.unsplashed.datasource.FirebaseProvider

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class MainViewPresenter: MvpBasePresenter<MainView>() {

    fun startLoadingData(){
        if (!isViewAttached){
            return
        }
        view?.showLoadingView()

        FirebaseProvider.instance.getRandomizedWallpapers()
    }




}