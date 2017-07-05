package com.xuie.imaginaryandroid.gui.meizhi;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface GankContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
