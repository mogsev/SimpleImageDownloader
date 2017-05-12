package com.mogsev.simpleimagedownloader.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class Pictures implements Parcelable {

    private static final String BUNDLE_NAME = Pictures.class.getSimpleName();

    @Expose
    @SerializedName("info")
    private Info info;

    @Expose
    @SerializedName("items")
    private List<Item> items;

    public Pictures() {

    }

    protected Pictures(final Parcel in) {
        info = in.readParcelable(getClass().getClassLoader());
        items = in.readArrayList(getClass().getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, int flags) {
        parcel.writeParcelable(info, flags);
        parcel.writeList(items);
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static final Parcelable.Creator<Pictures> CREATOR = new Parcelable.Creator<Pictures>() {
        @Override
        public Pictures createFromParcel(Parcel in) {
            return new Pictures(in);
        }

        @Override
        public Pictures[] newArray(int size) {
            return new Pictures[size];
        }
    };

    @Override
    public String toString() {
        return "Pictures{" +
                "info=" + info +
                ", items=" + items +
                '}';
    }
}
