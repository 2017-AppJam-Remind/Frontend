package com.example.parktaejun.remind.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public interface JSONService {

    @FormUrlEncoded
    @POST("/one")
    Call<Remind> one_upload(@Field("user_id") String user_id, @Field("user_password") String user_pw, @Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("/two")
    Call<Remind> two_upload(@Field("post_name") String post_name, @Field("post_title") String post_title, @Field("post_time") String post_time, @Field("post_context") String post_context);

    @FormUrlEncoded
    @POST("/three")
    Call<Remind> three_upload(@Field("post_name") String post_name, @Field("post_title") String post_title, @Field("post_time") String post_time, @Field("post_context") String post_context);

    @FormUrlEncoded
    @POST("/one")
    Call<List<Remind>> one_download();

    @FormUrlEncoded
    @POST("/two")
    Call<List<Remind>> two_download();

    @FormUrlEncoded
    @POST("/three")
    Call<List<Remind>> three_download();

}
