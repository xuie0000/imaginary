package xuk.imaginary.gui.gank.show

import xuk.imaginary.BasePresenter
import xuk.imaginary.BaseView
import xuk.imaginary.data.GankBean

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
