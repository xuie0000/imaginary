package com.xuie.imaginaryandroid.gui.nets.news

import com.xuie.imaginaryandroid.BasePresenter
import com.xuie.imaginaryandroid.BaseView
import com.xuie.imaginaryandroid.data.NetsSummary

/**
 * This specifies the contract between the view and the presenter.
 */
interface NewsListContract {

    interface View : BaseView<Presenter> {

        fun addList(isRefresh: Boolean, netsSummaries: List<NetsSummary>)
    }

    interface Presenter : BasePresenter {
        fun getList(isRefresh: Boolean)
    }
}
