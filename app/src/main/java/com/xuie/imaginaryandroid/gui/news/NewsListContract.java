package com.xuie.imaginaryandroid.gui.news;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.NetsSummary;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface NewsListContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh, List<NetsSummary> netsSummaries);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
