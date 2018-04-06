package com.xuie.imaginaryandroid.gui.nets.video

import com.xuie.imaginaryandroid.BasePresenter
import com.xuie.imaginaryandroid.BaseView
import com.xuie.imaginaryandroid.data.VideoBean

/**
 * This specifies the contract between the view and the presenter.
 */
interface VideosContract {

    interface View : BaseView<Presenter> {

        fun addList(isRefresh: Boolean, videoBeen: List<VideoBean>)
    }

    interface Presenter : BasePresenter {
        fun getList(type: String, isRefresh: Boolean)
    }
}
