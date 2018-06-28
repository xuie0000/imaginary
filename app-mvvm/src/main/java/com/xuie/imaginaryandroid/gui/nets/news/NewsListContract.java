package com.xuie.imaginaryandroid.gui.nets.news;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.NetsSummary;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface NewsListContract {

    interface View extends BaseView {

        void addList(boolean isRefresh, List<NetsSummary> netsSummaries);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
