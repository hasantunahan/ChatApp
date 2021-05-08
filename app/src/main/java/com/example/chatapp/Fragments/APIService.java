package com.example.chatapp.Fragments;


import com.example.chatapp.Notification.MyResponse;
import com.example.chatapp.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
           "Content-Type:application/json",
                    "Authorization:key=AAAA1-OyKTk:APA91bFcEh3IrPyesxDwUEOyfW7bsI7T24T-rbJ5ww7TKfRG8SRE9tGRZEY8Z-E6uSJdT_xLAWREQTTvNn2QHgGyBWRAoNdVF7s0HXrw2TbepIar2gawkY7VaAnCltQWw_VkujUl89EK"


            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
