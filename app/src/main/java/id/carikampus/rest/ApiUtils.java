package id.carikampus.rest;

import id.carikampus.service.ProdiService;

public class ApiUtils {

    // This is your server API server ip Address
    // If you use localhost, look for your computer IP Address
    public static final String API_URL = "http://192.168.100.140:8080/"; // 192.168.100.140:8080/

    // If you use emulator / AVD use this:
    //public static final String API_URL = "http://10.0.2.2:8080";

    private ApiUtils() {

    }

    public static ProdiService getProdiService() {
        return RetrofitClient.getClient(API_URL).create(ProdiService.class);
    }
}
