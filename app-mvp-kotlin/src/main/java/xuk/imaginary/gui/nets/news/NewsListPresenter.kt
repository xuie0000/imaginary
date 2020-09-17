package xuk.imaginary.gui.nets.news

import io.reactivex.android.schedulers.AndroidSchedulers
import xuk.imaginary.data.source.NETSRepository

/**
 * Created by xuie on 17-7-5.
 */

class NewsListPresenter(
    private val netsRepository: NETSRepository,
    private val netsView: NewsListContract.View
) : NewsListContract.Presenter {
  private var currentPage = 0

  init {
    this.netsView.presenter = this
  }

  override fun start() {
    getList(true)
  }

  override fun getList(isRefresh: Boolean) {
    if (isRefresh) {
      currentPage = 0
    } else {
      currentPage += 20
    }
    netsRepository.getNews(currentPage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ netsSummaries ->
          netsView.addList(isRefresh, netsSummaries)
        }, { e -> e.printStackTrace() })

  }
}
