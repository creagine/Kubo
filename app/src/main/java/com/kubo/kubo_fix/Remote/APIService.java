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
                    "Authorization:key=AAAAe7gO-4I:APA91bFf83jlr9gJjW_q3lir-YxhX6kN-3bhBCIWFvUVsuR5UuZSnDnzG9IOIPkJJvyDLUZTzfArxwnFqo6YYY084Hfjrm9Su8B6XZ2uNRLT3gLMIva3JgE1KKTK7A3GD6imUjn2T0CQ"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
