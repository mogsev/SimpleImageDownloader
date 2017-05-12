package com.mogsev.appmvp.presenters;

import com.mogsev.appmvp.network.model.Item;
import com.mogsev.appmvp.views.ItemView;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class ItemPresenter extends BasePresenter<Item, ItemView> {
    private static final String TAG = ItemPresenter.class.getSimpleName();

    @Override
    protected void updateView() {
        getView().setImage(mModel.getUrl());
        getView().setName(mModel.getName());
        getView().setDate(mModel.getDate());
    }
}
