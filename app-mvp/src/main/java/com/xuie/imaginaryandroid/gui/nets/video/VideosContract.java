package com.xuie.imaginary.gui.nets.video;

import com.xuie.imaginary.BasePresenter;
import com.xuie.imaginary.BaseView;
import com.xuie.imaginary.data.VideoBean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface VideosContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh, List<VideoBean> videoBeen);
    }

    interface Presenter extends BasePresenter {
        void getList(String type, boolean isRefresh);
    }
}
