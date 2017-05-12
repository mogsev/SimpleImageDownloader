package com.mogsev.appmvp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mogsev.appmvp.presenters.BasePresenter;
import com.mogsev.appmvp.viewholder.BaseViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public abstract class BaseRecyclerAdapter<M, P extends BasePresenter, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();

    protected final Map<Object, P> mPresenters;

    public BaseRecyclerAdapter() {
        mPresenters = new HashMap<>();
    }

    @NonNull
    protected P getPresenter(@NonNull M model) {
        Log.e(TAG, "Getting presenter for item " + getModelId(model));
        return mPresenters.get(getModelId(model));
    }

    @NonNull
    protected abstract P createPresenter(@NonNull M model);

    @NonNull
    protected abstract Object getModelId(@NonNull M model);

    @Override
    public void onViewRecycled(VH holder) {
        super.onViewRecycled(holder);

        holder.unbindPresenter();
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        // Sometimes, if animations are running on the itemView's children, the RecyclerView won't
        // be able to recycle the view. We should still unbind the presenter.
        holder.unbindPresenter();

        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindPresenter(getPresenter(getItem(position)));
    }

    protected abstract M getItem(int position);

}
