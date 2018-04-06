package com.xuie.imaginaryandroid.gui.nets.news

import com.xuie.imaginaryandroid.data.source.NETSRepository

import rx.android.schedulers.AndroidSchedulers

import com.xuie.imaginaryandroid.util.Utils.checkNotNull

/**
 * Created by xuie on 17-7-5.
 */

class NewsListPresenter(netsRepository: NETSRepository, netsView: NewsListContract.View) : NewsListContract.Presenter {
    private val netsRepository: NETSRepository
    private val netsView: NewsListContract.View
    private var currentPage = 0

    init {
        this.netsRepository = checkNotNull(netsRepository)
        this.netsView = checkNotNull(netsView)
        this.netsView.setPresenter(this)
    }

    @Override
    fun subscribe() {
        getList(true)
    }

    @Override
    fun unsubscribe() {

    }

    @Override
    fun getList(isRefresh: Boolean) {
        if (isRefresh) {
            currentPage = 0
        } else {
            currentPage += 20
        }
        netsRepository.getNews(currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ netsSummaries -> netsView.addList(isRefresh, netsSummaries) }, ???({ Throwable.printStackTrace() }))

    }
}
