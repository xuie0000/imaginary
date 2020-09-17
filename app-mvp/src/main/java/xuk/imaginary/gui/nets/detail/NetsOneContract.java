package xuk.imaginary.gui.nets.detail;

import xuk.imaginary.BasePresenter;
import xuk.imaginary.BaseView;
import xuk.imaginary.data.NetsDetail;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface NetsOneContract {

    interface View extends BaseView<Presenter> {
        void refreshNewsOne(NetsDetail netsDetail);
    }

    interface Presenter extends BasePresenter {
        void getNewsOneRequest(String postId);
    }
}
