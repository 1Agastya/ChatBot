package com.example.chatbot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("get")
    Call<MsgModel> getMessage(
            @Query("key") String apiKey,
            @Query("uid") String uid,
            @Query("msg") String message
    );
}

