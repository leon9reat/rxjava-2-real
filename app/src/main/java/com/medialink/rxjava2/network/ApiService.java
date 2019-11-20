package com.medialink.rxjava2.network;

import com.medialink.rxjava2.model.CrudRespon;
import com.medialink.rxjava2.model.Pimpinan;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "http://192.168.3.180:3000/api/";

    @GET("mas_list_pimpinan")
    Single<List<Pimpinan>> getAllPimpinan();

    @FormUrlEncoded
    @PATCH("mas_list_pimpinan/{id}")
    Completable updatePimpinan(@Path("id") int id,
                               @FieldMap HashMap<String, String> data);

    @DELETE("mas_list_pimpinan/{id}")
    Completable deletePimpinan(@Path("id") int id);

    @FormUrlEncoded
    @POST("mas_list_pimpinan")
    Single<CrudRespon> createPimpinan(@FieldMap HashMap<String, String> data);

}
