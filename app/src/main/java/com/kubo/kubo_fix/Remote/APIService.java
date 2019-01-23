package com.kubo.kubo_fix.Remote;

import com.kubo.kubo_fix.Model.MyResponse;
import com.kubo.kubo_fix.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAxpKy33k:APA91bHZygeGIUepDouUMrpPFt96d6MY8Kxgh3hcpQrqQ9KxpHXw-oAHwiqRXJylfNba4wpK-IsuH5v0MLZUSERsEIZxB-K91YrTTPTSrk4n0XS-LuH-5rr3xcCsNKxub9wZ-gUEVixH"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
