package xuk.imaginary.gui.gank.meizhi

import io.reactivex.android.schedulers.AndroidSchedulers
import xuk.imaginary.data.source.GankRepository

/**
 * Created by xuie on 17-7-5.
 */

class MeizhiPresenter(
    private val gankRepository: GankRepository,
    private val gankView: MeizhiContract.View
) : MeizhiContract.Presenter {
  init {
    this.gankView.presenter = this
  }

  private var currentPage = 1

  override fun start() {
    getList(true)
  }

  override fun getList(isRefresh: Boolean) {
    // get local data
    if (isRefresh) {
      currentPage = 1
    } else {
      currentPage++
    }
    gankRepository.get福利(currentPage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ meiZhis -> gankView.addList(isRefresh, meiZhis) },
            { e -> e.printStackTrace() })
    // get remote data

  }
}
