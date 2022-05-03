package Fragments;

import Notification.MyResponse;
import Notification.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAH4bTjBk:APA91bFnoO1gyCI2GXnsNLwke1hSk7CdgFAdZTa1Jh7ANTfAXnN7tyD3sfT-umfZZ8Wd2lseCYsRvYz5Yh_xvdI0fHA077EI78TkODQniUoY0B43DjxOnWLhat9zPMgllnypAPyyw95o"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}