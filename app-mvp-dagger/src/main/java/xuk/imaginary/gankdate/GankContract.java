package xuk.imaginary.gankdate;

import xuk.imaginary.BasePresenter;
import xuk.imaginary.BaseView;
import xuk.imaginary.data.GankBean;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface GankContract {

    interface View extends BaseView<Presenter> {

        void refresh(GankBean gb);
    }

    interface Presenter extends BasePresenter<View> {
        void getGank(int year, int month, int day);

        void getGank(String date);

        void takeView(GankContract.View view);

        void dropView();

    }
}
