package com.example.parktaejun.remind.Server;

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
    Call<Remind> one_upload(@Field("name") String image, @Field("weather") String user_pw, @Field("memo") String user_name, @Field("file") String file);

    @FormUrlEncoded
    @POST("/two")
    Call<Remind> two_upload(@Field("name") String image, @Field("weather") String user_pw, @Field("memo") String user_name, @Field("file") String file);

    @FormUrlEncoded
    @POST("/three")
    Call<Remind> three_upload(@Field("name") String image, @Field("weather") String user_pw, @Field("memo") String user_name, @Field("file") String file);

    @FormUrlEncoded
    @POST("/get")
    Call<Remind> download(@Field("location") String location);

    @Multipart
    @POST("/one")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );
}
