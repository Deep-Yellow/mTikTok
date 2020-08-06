package com.example.mtiktok.widget;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // https://wanandroid.com/wxarticle/chapters/json
    @GET("api/invoke/video/invoke/video")
    Call<ArrayList<VideoInfo.Video>> getVideos();

}
