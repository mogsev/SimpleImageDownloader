package com.mogsev.appmvp.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class Info implements Parcelable {

    public static final String BUNDLE_NAME = Info.class.getSimpleName();

    @Expose
    @SerializedName("limit")
    private int limit;

    @Expose
    @SerializedName("offset")
    private int offset;

    @Expose
    @SerializedName("total_count")
    private int totalCount;

    @Expose
    @SerializedName("view")
    private int view;

    public Info() {

    }

    protected Info(final Parcel in) {
        limit = in.readInt();
        offset = in.readInt();
        totalCount = in.readInt();
        view = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, int flags) {
        parcel.writeInt(limit);
        parcel.writeInt(offset);
        parcel.writeInt(totalCount);
        parcel.writeInt(view);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    @Override
    public String toString() {
        return "Info{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", totalCount=" + totalCount +
                ", view=" + view +
                '}';
    }
}
