package com.example.parktaejun.remind.Server;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public interface JSONService {

    @FormUrlEncoded
    @POST("/one")
    Call<RemindUp> one_upload(@Field("name") String name, @Field("weather") String weather, @Field("memo") String memo, @Field("file") File file);

    @FormUrlEncoded
    @POST("/two")
    Call<RemindUp> two_upload(@Field("name") String name, @Field("weather") String weather, @Field("memo") String memo, @Field("file") File file);

    @FormUrlEncoded
    @POST("/three")
    Call<RemindUp> three_upload(@Field("name") String name, @Field("weather") String weather, @Field("memo") String memo, @Field("file") File file);

    @FormUrlEncoded
    @POST("/get")
    Call<Remind> download(@Field("location") String location);
}
