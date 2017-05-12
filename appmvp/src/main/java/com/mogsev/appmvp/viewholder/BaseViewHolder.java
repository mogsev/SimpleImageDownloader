package com.mogsev.appmvp.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mogsev.appmvp.presenters.BasePresenter;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public abstract class BaseViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder  {

    protected P mPresenter;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        mPresenter = presenter;
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        mPresenter = null;
    }
}
