package xuk.imaginary.gui.gank.show

import io.reactivex.android.schedulers.AndroidSchedulers
import xuk.imaginary.data.source.GankRepository

/**
 * Created by xuie on 17-7-5.
 */

class GankPresenter(
    private val gankRepository: GankRepository,
    private val meizhiView: GankContract.View
) : GankContract.Presenter {
    private val currentPage = 1

    init {
        this.meizhiView.presenter = this
    }

    override fun start() {
//        getList(true);
    }

    override fun getGank(year: Int, month: Int, day: Int) {

    }

    override fun getGank(date: String) {
        gankRepository.getDay(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gankBean -> meizhiView.refresh(gankBean) },
                        { e -> e.printStackTrace()})
    }
}
