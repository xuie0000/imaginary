package com.xuie.imaginaryandroid.gui.nets.video

import com.xuie.imaginaryandroid.data.source.NETSRepository

import rx.android.schedulers.AndroidSchedulers

import com.xuie.imaginaryandroid.util.Utils.checkNotNull

/**
 * Created by xuie on 17-7-5.
 */

class VideosPresenter(netsRepository: NETSRepository, videosView: VideosContract.View) : VideosContract.Presenter {
    private val netsRepository: NETSRepository
    private val videosView: VideosContract.View
    private var currentPage = 0

    init {
        this.netsRepository = checkNotNull(netsRepository)
        this.videosView = checkNotNull(videosView)
        this.videosView.setPresenter(this)
    }

    @Override
    fun subscribe() {
    }

    @Override
    fun unsubscribe() {

    }

    @Override
    fun getList(type: String, isRefresh: Boolean) {
        if (isRefresh) {
            currentPage = 0
        } else {
            currentPage += 1
        }
        netsRepository.getVideoList(type, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoBeen -> videosView.addList(isRefresh, videoBeen) }, ???({ Throwable.printStackTrace() }))

    }
}
