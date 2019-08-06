package xuk.imaginary.gui.nets.news;

import xuk.imaginary.BasePresenter;
import xuk.imaginary.BaseView;
import xuk.imaginary.data.NetsSummary;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 *
 * @author xuie
 */
public interface NewsListContract {

    interface View extends BaseView<Presenter> {

        void addList(boolean isRefresh, List<NetsSummary> netsSummaries);
    }

    interface Presenter extends BasePresenter {
        void getList(boolean isRefresh);
    }
}
