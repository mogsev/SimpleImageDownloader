package com.mogsev.appmvp.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public abstract class BasePresenter<M, V> implements Presenter<M, V> {
    private static final String TAG = BasePresenter.class.getSimpleName();

    protected M mModel;
    private WeakReference<V> mView;

    @Override
    public void setModel(@NonNull M model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        resetState();
        mModel = model;
        if (setupDone()) {
            updateView();
        }
    }

    @Override
    public void bindView(@NonNull V view) {
        if (view == null) {
            throw new IllegalArgumentException("View cannot be null");
        }
        mView = new WeakReference<>(view);
        if (setupDone()) {
            updateView();
        }
    }

    @Override
    public void unbindView() {
        mView = null;
    }

    protected void resetState() {
    }

    protected V getView() {
        if (mView == null) {
            return null;
        } else {
            return mView.get();
        }
    }

    protected abstract void updateView();

    protected boolean setupDone() {
        return getView() != null && mModel != null;
    }

}
