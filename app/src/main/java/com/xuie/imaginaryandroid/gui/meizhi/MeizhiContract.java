package com.xuie.imaginaryandroid.gui.meizhi;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.福利Bean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MeizhiContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh, List<福利Bean> meiZhis);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
