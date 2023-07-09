package com.doseApp.dose.dose.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                     "Content-Type:application/json",
                    "Authorization:key=AAAAeyFDRKI:APA91bGlXqZeUOtlo9jAcMZkJiSHR5JrR5ruyuFltXQSnx96wXScRSctIp2YvL1quXdvfyQ5PqXBn4a6mG2RkeZWtvJ1j7IK8HiAW84z9M_gpbhGQL2Eu2cNmk3W7Zaa61z6ZwXSGzk_" // Your server key refer to video for finding your server key
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

