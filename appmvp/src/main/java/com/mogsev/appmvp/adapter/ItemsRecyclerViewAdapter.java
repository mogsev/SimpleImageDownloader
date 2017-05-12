package com.mogsev.appmvp.adapter;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mogsev.appmvp.R;
import com.mogsev.appmvp.network.model.Item;
import com.mogsev.appmvp.presenters.ItemPresenter;
import com.mogsev.appmvp.viewholder.ItemViewHolder;
import com.mogsev.appmvp.viewholder.LoadingViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class ItemsRecyclerViewAdapter extends BaseRecyclerListAdapter<Item, ItemPresenter, ItemViewHolder> {
    private static final String TAG = ItemsRecyclerViewAdapter.class.getSimpleName();

    public static final int TYPE_VIEW_HOLDER_ITEM = 0;
    public static final int TYPE_VIEW_HOLDER_LOADING = 1;

    private static final String BUNDLE_LIST_ITEMS = "bundle_list_items";

    private final Item mLoadingItem = new Item(0);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SortType.ASCENDING, SortType.DESCENDING})
    public @interface SortType {
        public static final int ASCENDING = 0;
        public static final int DESCENDING = 1;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        ItemViewHolder viewHolder = null;
        View view = null;
        switch (viewType) {
            case TYPE_VIEW_HOLDER_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_item, parent, false);
                viewHolder = new ItemViewHolder(view);
                break;
            case TYPE_VIEW_HOLDER_LOADING:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_loading, parent, false);
                viewHolder = new LoadingViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @NonNull
    @Override
    protected ItemPresenter createPresenter(@NonNull Item model) {
        ItemPresenter presenter = new ItemPresenter();
        presenter.setModel(model);
        return presenter;
    }

    @NonNull
    @Override
    protected Object getModelId(@NonNull Item model) {
        return model.getId();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = getItem(position);
        if (item.getId() != 0) {
            holder.bindPresenter(getPresenter(getItem(position)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        Item item = mModels.get(position);
        return (item.getId() != 0) ? TYPE_VIEW_HOLDER_ITEM : TYPE_VIEW_HOLDER_LOADING;
    }

    public synchronized void addProgress() {
        Log.i(TAG, "addProgress");
        addItem(mLoadingItem);
    }

    public synchronized void removeProgress() {
        Log.i(TAG, "removeProgress");
        removeItem(mLoadingItem);
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_LIST_ITEMS, (ArrayList) mModels);
    }

    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        addAll(savedInstanceState.getParcelableArrayList(BUNDLE_LIST_ITEMS));
    }

    public void onSort(@SortType int type) {
        switch (type) {
            case SortType.ASCENDING:
                Collections.sort(mModels);
                break;
            case SortType.DESCENDING:
                Collections.sort(mModels, Collections.reverseOrder());
                break;
        }
        notifyDataSetChanged();
    }

}
