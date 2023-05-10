package com.example.dose.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                     "Content-Type:application/json",
                    "Authorization:key=AAAA7fdW6tc:APA91bHIPa8TGP4TMwuBxMmt1YVXbsdhlAAdnJuUvkirAg3Szrt-kQkXa0h16e24Tsl9E0LCRL2vuK1oxjdUoKoVqzyiixOPrCrKOu7lbT4OFtaigF2P5wLNKBKft9dwjF5wHQD4-UX-" // Your server key refer to video for finding your server key
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

