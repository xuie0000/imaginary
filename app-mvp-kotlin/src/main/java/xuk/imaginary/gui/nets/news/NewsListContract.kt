package xuk.imaginary.gui.nets.news

import xuk.imaginary.BasePresenter
import xuk.imaginary.BaseView
import xuk.imaginary.data.NetsSummary

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
