package com.dmt.dangtus.fo4t.url;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ClubCareerUrl {
//    private static String urlBase = "http://192.168.1.3/quanlicauthu/club_career/";
    private static String urlBase = "https://top1elo.000webhostapp.com/club_career/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String getUrlBase() {
        return urlBase;
    }

    public static void getByIDPlayerFootball(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(urlBase + url, params, responseHandler);
    }
}