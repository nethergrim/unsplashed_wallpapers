package com.nethergrim.unsplashed.ui.main

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.nethergrim.unsplashed.datasource.FirebaseProvider
import rx.android.schedulers.AndroidSchedulers

/**
 * @author Andrey Drobyazko (c2q9450@gmail.com).
 * All rights reserved.
 */
class MainViewPresenter : MvpBasePresenter<MainView>() {

    fun startLoadingData() {
        if (!isViewAttached) {
            return
        }
        view?.showLoadingView()

        FirebaseProvider.instance
                .getRandomizedWallpapers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (isViewAttached) {
                        if (it.isEmpty()) {
                            view?.showErrorView()
                        } else {
                            view?.showData(it)
                        }
                    }
                }, {
                    print(it)
                    if (isViewAttached) {
                        view?.showErrorView()
                    }
                })
    }

}