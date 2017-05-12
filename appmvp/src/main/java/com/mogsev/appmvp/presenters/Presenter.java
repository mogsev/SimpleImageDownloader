package com.mogsev.appmvp.presenters;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public interface Presenter<M, V> {

    public void setModel(M model);

    public void bindView(V view);

    public void unbindView();

}
