package com.xuie.imaginaryandroid.gui.gank.show

import com.xuie.imaginaryandroid.BasePresenter
import com.xuie.imaginaryandroid.BaseView
import com.xuie.imaginaryandroid.data.GankBean

/**
 * This specifies the contract between the view and the presenter.
 */
interface GankContract {

    interface View : BaseView<Presenter> {

        fun refresh(gb: GankBean)
    }

    interface Presenter : BasePresenter {
        fun getGank(year: Int, month: Int, day: Int)

        fun getGank(date: String)
    }
}
