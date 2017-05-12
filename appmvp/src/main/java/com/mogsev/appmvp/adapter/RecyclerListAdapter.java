package com.mogsev.appmvp.adapter;

import java.util.Collection;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public interface RecyclerListAdapter<M> {

    public void clearAndAddAll(Collection<M> data);

    public void addAll(Collection<M> data);

    public void addItem(M item);

    public void updateItem(M item);

    public void removeItem(M item);

}
