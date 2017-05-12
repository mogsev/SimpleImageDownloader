package com.mogsev.simpleimagedownloader.network.model;

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InfoTest {

    @Test
    public void testInfoParcelable_Success() {
        Info info = new Info();
        info.setLimit(10);
        info.setOffset(0);
        info.setTotalCount(100);
        info.setView(10);

        Parcel parcel = Parcel.obtain();
        info.writeToParcel(parcel, info.describeContents());
        parcel.setDataPosition(0);

        Info createdFromParcel = Info.CREATOR.createFromParcel(parcel);
        assertTrue(createdFromParcel != null);
        assertEquals(info.getLimit(), createdFromParcel.getLimit());
        assertEquals(info.getOffset(), createdFromParcel.getOffset());
        assertEquals(info.getTotalCount(), createdFromParcel.getTotalCount());
        assertEquals(info.getView(), createdFromParcel.getView());
    }

}
