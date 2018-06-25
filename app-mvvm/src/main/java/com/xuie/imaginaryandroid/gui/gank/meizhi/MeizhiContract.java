package com.xuie.imaginaryandroid.gui.gank.meizhi;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.BaseBean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface MeizhiContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh, List<BaseBean> meiZhis);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
