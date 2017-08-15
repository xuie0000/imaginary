package com.xuie.imaginaryandroid.gui.show;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.GankBean;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface GankContract {

    interface View extends BaseView<Presenter> {

        void refresh(GankBean gb);
    }

    interface Presenter extends BasePresenter {
        void getGank(int year, int month, int day);

        void getGank(String date);
    }
}
