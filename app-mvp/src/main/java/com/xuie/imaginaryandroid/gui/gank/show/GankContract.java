package xuk.imaginary.gui.gank.show;

import xuk.imaginary.BasePresenter;
import xuk.imaginary.BaseView;
import xuk.imaginary.data.GankBean;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface GankContract {

    interface View extends BaseView<Presenter> {

        void refresh(GankBean gb);
    }

    interface Presenter extends BasePresenter {
        void getGank(int year, int month, int day);

        void getGank(String date);
    }
}
