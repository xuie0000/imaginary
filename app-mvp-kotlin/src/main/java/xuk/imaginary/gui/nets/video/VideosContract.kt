package xuk.imaginary.gui.nets.video

import xuk.imaginary.BasePresenter
import xuk.imaginary.BaseView
import xuk.imaginary.data.VideoBean

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
