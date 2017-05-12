package com.mogsev.appmvp.adapter;

import android.util.Log;

import com.mogsev.appmvp.presenters.BasePresenter;
import com.mogsev.appmvp.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public abstract class BaseRecyclerListAdapter<M, P extends BasePresenter, VH extends BaseViewHolder<P>> extends BaseRecyclerAdapter<M, P, VH> implements RecyclerListAdapter<M> {
    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();

    protected final List<M> mModels;

    public BaseRecyclerListAdapter() {
        mModels = new ArrayList<M>();
    }

    public BaseRecyclerListAdapter(List<M> list) {
        mModels = list;
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    @Override
    protected M getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public void clearAndAddAll(Collection<M> data) {
        mModels.clear();
        mPresenters.clear();

        for (M item : data) {
            addInternal(item);
        }

        notifyDataSetChanged();
    }

    @Override
    public void addAll(Collection<M> data) {
        Log.i(TAG, "addAll");
        for (M item : data) {
            addInternal(item);
        }

        int addedSize = data.size();
        int oldSize = mModels.size() - addedSize;
        notifyItemRangeInserted(oldSize, addedSize);
    }

    @Override
    public void addItem(M item) {
        addInternal(item);
        notifyItemInserted(mModels.size());
    }

    @Override
    public void updateItem(M item) {
        Object modelId = getModelId(item);

        // Swap the model
        int position = getItemPosition(item);
        if (position >= 0) {
            mModels.remove(position);
            mModels.add(position, item);
        }

        // Swap the presenter
        P existingPresenter = mPresenters.get(modelId);
        if (existingPresenter != null) {
            existingPresenter.setModel(item);
        }

        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    @Override
    public void removeItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            mModels.remove(item);
        }
        mPresenters.remove(getModelId(item));

        if (position >= 0) {
            notifyItemRemoved(position);
        }
    }

    private int getItemPosition(M item) {
        Object modelId = getModelId(item);

        int position = -1;
        for (int i = 0; i < mModels.size(); i++) {
            M model = mModels.get(i);
            if (getModelId(model).equals(modelId)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void addInternal(M item) {
        Log.i(TAG, "addInternal " + item);
        System.err.println("Adding item " + getModelId(item));
        mModels.add(item);
        mPresenters.put(getModelId(item), createPresenter(item));
    }
}
