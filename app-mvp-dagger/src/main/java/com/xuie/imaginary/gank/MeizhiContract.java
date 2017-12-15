package com.xuie.imaginary.gank;

import com.xuie.imaginary.BasePresenter;
import com.xuie.imaginary.BaseView;
import com.xuie.imaginary.data.BaseBean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MeizhiContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh, List<BaseBean> meiZhis);
    }

    interface Presenter extends BasePresenter<View> {
        void getList(boolean isRefresh);

        void takeView(MeizhiContract.View view);

        void dropView();
    }
}
