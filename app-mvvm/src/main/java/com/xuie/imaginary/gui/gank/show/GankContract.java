package com.xuie.imaginary.gui.gank.show;

import com.xuie.imaginary.BasePresenter;
import com.xuie.imaginary.BaseView;
import com.xuie.imaginary.data.GankBean;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface GankContract {

    interface View extends BaseView {

        void refresh(GankBean gb);
    }

    interface Presenter extends BasePresenter {
        void getGank(int year, int month, int day);

        void getGank(String date);
    }
}
