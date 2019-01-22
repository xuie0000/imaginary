package com.xuie.imaginary.gui.nets.detail;

import com.xuie.imaginary.BasePresenter;
import com.xuie.imaginary.BaseView;
import com.xuie.imaginary.data.NetsDetail;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface NetsOneContract {

    interface View extends BaseView {
        void refreshNewsOne(NetsDetail netsDetail);
    }

    interface Presenter extends BasePresenter {
        void getNewsOneRequest(String postId);
    }
}
