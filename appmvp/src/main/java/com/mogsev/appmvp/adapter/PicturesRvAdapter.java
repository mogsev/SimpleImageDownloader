package com.mogsev.appmvp.adapter;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mogsev.appmvp.R;
import com.mogsev.appmvp.network.model.Item;
import com.mogsev.appmvp.utils.DateHelper;
import com.squareup.picasso.Picasso;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class PicturesRvAdapter extends RecyclerView.Adapter {
    private static final String TAG = PicturesRvAdapter.class.getSimpleName();

    private static final String BUNDLE_LIST_ITEMS = "bundle_list_items";

    private static final int TYPE_VIEW_HOLDER_ITEM = 0;
    private static final int TYPE_VIEW_HOLDER_LOADING = 1;

    private List<Item> mList = new ArrayList<>();

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SortType.ASCENDING, SortType.DESCENDING})
    public @interface SortType {
        public static final int ASCENDING = 0;
        public static final int DESCENDING = 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder = null;
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Item item = mList.get(position);
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            String value = null;
            value = item.getUrl();
            if (!TextUtils.isEmpty(value)) {
                Picasso.with(viewHolder.imageView.getContext())
                        .load(value)
                        .error(R.drawable.ic_image_broken_gray_24dp)
                        .into(viewHolder.imageView);
            }
            value = item.getName();
            if (!TextUtils.isEmpty(value)) {
                viewHolder.textName.setText(value);
            }
            value = DateHelper.getTime(item.getDate());
            viewHolder.textTime.setText(value);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) != null ? TYPE_VIEW_HOLDER_ITEM : TYPE_VIEW_HOLDER_LOADING;
    }

    public synchronized void addItem(@NonNull Item item, int position) {
        Log.i(TAG, "addItem: " + position);
        mList.add(item);
        notifyItemInserted(position);
    }

    public synchronized void addItems(@NonNull List<Item> list) {
        for (Item item : list) {
            addItem(item, mList.size());
        }
    }

    public synchronized void addProgress() {
        Log.i(TAG, "addProgress");
        mList.add(null);
        notifyItemInserted(mList.size() - 1);
    }

    public synchronized void removeProgress() {
        Log.i(TAG, "removeProgress");
        int pos = mList.size() - 1;
        mList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setList(@Nullable List<Item> list) {
        if (list != null && !list.isEmpty()) {
            mList = list;
            notifyDataSetChanged();
        }
    }

    public List<Item> getList() {
        return mList;
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(BUNDLE_LIST_ITEMS, (ArrayList) mList);
    }

    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        setList(savedInstanceState.getParcelableArrayList(BUNDLE_LIST_ITEMS));
    }

    public void onSort(@SortType int type) {
        switch (type) {
            case SortType.ASCENDING:
                Collections.sort(mList);
                break;
            case SortType.DESCENDING:
                Collections.sort(mList, Collections.reverseOrder());
                break;
        }
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textName;
        public TextView textTime;

        public ItemViewHolder(final View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);
            textName = (TextView) itemView.findViewById(R.id.text_name);
            textTime = (TextView) itemView.findViewById(R.id.text_time);
        }

    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_progress_loading);
        }

    }

}
