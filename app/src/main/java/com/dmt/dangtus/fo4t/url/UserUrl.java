package com.dmt.dangtus.fo4t.url;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserUrl {
    private static String urlBase = "https://top1elo.000webhostapp.com/user/";
//    private static String urlBase = "http://192.168.1.3/quanlicauthu/user/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String getUrlBase() {
        return urlBase;
    }

    public static void checkAccount(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(urlBase + url, params, responseHandler);
    }
}
