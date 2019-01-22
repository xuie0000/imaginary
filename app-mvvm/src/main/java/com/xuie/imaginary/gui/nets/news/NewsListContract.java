package com.xuie.imaginary.gui.nets.news;

import com.xuie.imaginary.BasePresenter;
import com.xuie.imaginary.BaseView;
import com.xuie.imaginary.data.NetsSummary;

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
