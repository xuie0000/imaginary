package com.xuie.imaginaryandroid.gui.meizhi;

/**
 * Created by xuie on 17-7-5.
 */

public class GankPresenter implements GankContract.Presenter  {

    @Override
    public void subscribe() {
        getList(true);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getList(boolean isRefresh) {
        // get local data

        // get remote data

    }
}
