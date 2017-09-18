package com.xuie.imaginaryandroid.gui.nets.detail;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.NetsDetail;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface NetsOneContract {

    interface View extends BaseView<Presenter> {
        void refreshNewsOne(NetsDetail netsDetail);
    }

    interface Presenter extends BasePresenter {
        void getNewsOneRequest(String postId);
    }
}
