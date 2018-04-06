package com.xuie.imaginaryandroid.gui.gank.meizhi

import com.xuie.imaginaryandroid.BasePresenter
import com.xuie.imaginaryandroid.BaseView
import com.xuie.imaginaryandroid.data.BaseBean

/**
 * This specifies the contract between the view and the presenter.
 */
interface MeizhiContract {

    interface View : BaseView<Presenter> {

        fun addList(isRefresh: Boolean, meiZhis: List<BaseBean>)
    }

    interface Presenter : BasePresenter {
        fun getList(isRefresh: Boolean)
    }
}
