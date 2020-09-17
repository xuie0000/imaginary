package xuk.imaginary.gank;

import java.util.List;

import xuk.imaginary.BasePresenter;
import xuk.imaginary.BaseView;
import xuk.imaginary.data.BaseBean;

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
