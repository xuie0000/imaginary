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
        /**
         * 添加妹子列表
         *
         * @param isRefresh true : 重新刷新； false : 加载
         * @param meiZhis   the mei zhis
         */
        void addList(boolean isRefresh, List<BaseBean> meiZhis);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取列表
         *
         * @param isRefresh the is refresh
         */
        void getList(boolean isRefresh);
    }
}
