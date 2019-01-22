package com.xuie.imaginary.gui.gank.meizhi;

import com.xuie.imaginary.BasePresenter;
import com.xuie.imaginary.BaseView;
import com.xuie.imaginary.data.BaseBean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface MeizhiContract {

    interface View extends BaseView {
        void addList(boolean isRefresh, List<BaseBean> meiZhis);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
