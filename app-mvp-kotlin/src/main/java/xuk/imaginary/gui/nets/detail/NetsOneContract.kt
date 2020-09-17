package xuk.imaginary.gui.nets.detail

import xuk.imaginary.BasePresenter
import xuk.imaginary.BaseView
import xuk.imaginary.data.NetsDetail

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
