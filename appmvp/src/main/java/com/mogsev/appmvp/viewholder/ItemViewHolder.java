package com.mogsev.appmvp.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mogsev.appmvp.R;
import com.mogsev.appmvp.presenters.ItemPresenter;
import com.mogsev.appmvp.utils.DateHelper;
import com.mogsev.appmvp.views.ItemView;
import com.squareup.picasso.Picasso;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class ItemViewHolder extends BaseViewHolder<ItemPresenter> implements ItemView {
    private static final String TAG = ItemViewHolder.class.getSimpleName();

    private ImageView mImageView;
    private TextView mTextName;
    private TextView mTextTime;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mImageView = (ImageView) itemView.findViewById(R.id.image_item);
        mTextName = (TextView) itemView.findViewById(R.id.text_name);
        mTextTime = (TextView) itemView.findViewById(R.id.text_time);
    }

    @Override
    public void setImage(String url) {
        if (mImageView == null) return;
        if (!TextUtils.isEmpty(url)) {
            Picasso.with(mImageView.getContext())
                    .load(url)
                    .error(R.drawable.ic_image_broken_gray_24dp)
                    .into(mImageView);
        }
    }

    @Override
    public void setName(String name) {
        if (mTextName == null) return;
        mTextName.setText(name);
    }

    @Override
    public void setDate(long date) {
        if (mTextTime == null) return;
        mTextTime.setText(DateHelper.getTime(date));
    }
}
