package com.xuie.imaginaryandroid.gui.nets.detail

import com.xuie.imaginaryandroid.data.source.NETSRepository

import rx.android.schedulers.AndroidSchedulers

import com.xuie.imaginaryandroid.util.Utils.checkNotNull

/**
 * Created by xuie on 17-7-5.
 */

class NetsOnePresenter(netsRepository: NETSRepository, newsOneView: NetsOneContract.View) : NetsOneContract.Presenter {
    private val netsRepository: NETSRepository
    private val newsOneView: NetsOneContract.View
    private val currentPage = 1

    init {
        this.netsRepository = checkNotNull(netsRepository)
        this.newsOneView = checkNotNull(newsOneView)
        this.newsOneView.setPresenter(this)
    }

    @Override
    fun subscribe() {
    }

    @Override
    fun unsubscribe() {

    }

    @Override
    fun getNewsOneRequest(postId: String) {
        netsRepository.getNewDetail(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ netsDetail -> newsOneView.refreshNewsOne(netsDetail) },
                        ???({ Throwable.printStackTrace() }))
    }
}
