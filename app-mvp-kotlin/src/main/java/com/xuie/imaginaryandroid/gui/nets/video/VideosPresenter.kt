package com.xuie.imaginaryandroid.gui.nets.video

import com.xuie.imaginaryandroid.data.source.NETSRepository
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by xuie on 17-7-5.
 */

class VideosPresenter(
        private val netsRepository: NETSRepository,
        private val videosView: VideosContract.View
) : VideosContract.Presenter {
    private var currentPage = 0

    init {
        this.videosView.presenter = this
    }

    override fun start() {
    }

    override fun getList(type: String, isRefresh: Boolean) {
        if (isRefresh) {
            currentPage = 0
        } else {
            currentPage += 1
        }
        netsRepository.getVideoList(type, currentPage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoBeen -> videosView.addList(isRefresh, videoBeen) },
                        { e -> e.printStackTrace()})

    }
}
