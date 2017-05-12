package com.mogsev.appmvp.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public final class PaginationHelper {

    public static boolean isDownPagination(@NonNull LinearLayoutManager layoutManager) {
        if (layoutManager == null) {
            throw new IllegalArgumentException("LinearLayoutManager cannot be null");
        }
        int visibleItemCount = layoutManager.getChildCount();;
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
            return true;
        }
        return false;
    }

}
