package com.mogsev.appmvp.network;


import com.mogsev.appmvp.network.model.Pictures;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public interface ApiService {

    public static final String BASE_URL = "http://valentyn.top/";

    @GET("api/picture")
    Call<Pictures> getCallOfPictures(@Query("limit") int limit, @Query("offset") int offset);

    @GET("api/picture")
    Observable<Pictures> getObservableOfPictures(@Query("limit") int limit, @Query("offset") int offset);

}
