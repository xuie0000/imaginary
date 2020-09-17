package xuk.imaginary.gui.nets.detail

import io.reactivex.android.schedulers.AndroidSchedulers
import xuk.imaginary.data.source.NETSRepository

/**
 * Created by xuie on 17-7-5.
 */

class NetsOnePresenter(
    private val netsRepository: NETSRepository,
    private val newsOneView: NetsOneContract.View
) : NetsOneContract.Presenter {
  private val currentPage = 1

  init {
    this.newsOneView.presenter = this
  }

  override fun start() {
  }

  override fun getNewsOneRequest(postId: String) {
    netsRepository.getNewDetail(postId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ netsDetail -> newsOneView.refreshNewsOne(netsDetail) },
            { e -> e.printStackTrace() })
  }
}
