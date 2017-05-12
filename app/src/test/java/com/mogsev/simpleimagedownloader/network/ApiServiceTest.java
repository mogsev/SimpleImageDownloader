package com.mogsev.simpleimagedownloader.network;

import com.mogsev.simpleimagedownloader.network.model.Pictures;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */

public class ApiServiceTest {
    private static final String TAG = ApiServiceTest.class.getSimpleName();

    @Test
    public void getCallOfPictures_Success() {
        int offset = 10;
        for (int i = 0;;) {
            Call<Pictures> call = Api.API.getCallOfPictures(10, i);
            try {
                Response<Pictures> response = call.execute();
                Pictures result = response.body();
                assertTrue(response.isSuccessful());
                assertTrue(result != null);
                System.out.println(TAG + " Body: " + result.toString());
                if (i + offset < result.getInfo().getTotalCount()) {
                    i = i + offset;
                } else {
                    break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Test
    public void getObservableOfPictures_Success() {

    }

}
