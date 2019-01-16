package com.kubo.kubo_fix.Common;

import com.kubo.kubo_fix.Remote.APIService;
import com.kubo.kubo_fix.Remote.RetrofitClient;

public class Common {

    public static String barbershopSelected = "";
    public static String barbermanSelected = "";
    public static String serviceSelected = "";
    public static String jadwalSelected = "";
    public static String orderSelected = "";
    public static String currentUser = "";

    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
