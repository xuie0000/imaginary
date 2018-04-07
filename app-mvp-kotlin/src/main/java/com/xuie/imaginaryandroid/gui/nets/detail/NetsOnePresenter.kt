package com.xuie.imaginaryandroid.gui.nets.detail

import com.xuie.imaginaryandroid.data.source.NETSRepository

import io.reactivex.android.schedulers.AndroidSchedulers

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

    override fun subscribe() {
    }

    override fun unsubscribe() {

    }

    override fun getNewsOneRequest(postId: String) {
        netsRepository.getNewDetail(postId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ netsDetail -> newsOneView.refreshNewsOne(netsDetail) },
                        { e -> e.printStackTrace()})
    }
}
