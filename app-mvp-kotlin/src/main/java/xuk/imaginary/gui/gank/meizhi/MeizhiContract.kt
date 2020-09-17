package xuk.imaginary.gui.gank.meizhi

import xuk.imaginary.BasePresenter
import xuk.imaginary.BaseView
import xuk.imaginary.data.BaseBean

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
