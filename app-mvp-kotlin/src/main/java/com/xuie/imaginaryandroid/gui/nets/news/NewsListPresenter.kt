package com.xuie.imaginaryandroid.gui.nets.news

import com.xuie.imaginaryandroid.data.source.NETSRepository

import io.reactivex.android.schedulers.AndroidSchedulers

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

    override fun subscribe() {
        getList(true)
    }

    override fun unsubscribe() {

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
                    netsView.addList(isRefresh, netsSummaries) }, { e -> e.printStackTrace()})

    }
}
