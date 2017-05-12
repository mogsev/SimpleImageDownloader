package com.mogsev.appmvp.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public final class DateHelper {
    private static final String TAG = DateHelper.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({Format.DDMMYYYY, Format.DDMMYYYYHHMM})
    public @interface Format {
        public static final String DDMMYYYY = "dd.MM.yyyy";
        public static final String DDMMYYYYHHMM = "dd.MM.yyyy HH:mm";
    }

    @NonNull
    public static String getTime(long timestamp) {
        if (timestamp == 0) {
            return "";
        }
        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat format = new SimpleDateFormat(Format.DDMMYYYYHHMM);
        return format.format(date);
    }

    @NonNull
    public static String getTime(long timestamp, @Format String dateFormat) {
        if (timestamp == 0) {
            return "";
        }
        if (TextUtils.isEmpty(dateFormat)) {
            return getTime(timestamp);
        }
        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

}
