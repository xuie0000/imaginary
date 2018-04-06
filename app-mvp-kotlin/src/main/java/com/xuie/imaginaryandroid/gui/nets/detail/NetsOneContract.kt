package com.xuie.imaginaryandroid.gui.nets.detail

import com.xuie.imaginaryandroid.BasePresenter
import com.xuie.imaginaryandroid.BaseView
import com.xuie.imaginaryandroid.data.NetsDetail

/**
 * This specifies the contract between the view and the presenter.
 */
interface NetsOneContract {

    interface View : BaseView<Presenter> {
        fun refreshNewsOne(netsDetail: NetsDetail)
    }

    interface Presenter : BasePresenter {
        fun getNewsOneRequest(postId: String)
    }
}
