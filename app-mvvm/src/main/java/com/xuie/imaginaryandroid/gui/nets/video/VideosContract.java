package com.xuie.imaginaryandroid.gui.nets.video;

import com.xuie.imaginaryandroid.BasePresenter;
import com.xuie.imaginaryandroid.BaseView;
import com.xuie.imaginaryandroid.data.VideoBean;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface VideosContract {

    interface View extends BaseView {

        void addList(boolean isRefresh, List<VideoBean> videoBeen);
    }

    interface Presenter extends BasePresenter {
        void getList(String type, boolean isRefresh);
    }
}
