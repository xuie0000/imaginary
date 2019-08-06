package xuk.imaginary.gui.nets.video;

import xuk.imaginary.BasePresenter;
import xuk.imaginary.BaseView;
import xuk.imaginary.data.VideoBean;

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
