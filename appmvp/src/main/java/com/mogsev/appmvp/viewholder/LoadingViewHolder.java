package com.mogsev.appmvp.viewholder;

import android.view.View;
import android.widget.LinearLayout;

import com.mogsev.appmvp.R;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class LoadingViewHolder extends ItemViewHolder {

    private final LinearLayout linearLayout;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.linear_progress_loading);
    }

}
