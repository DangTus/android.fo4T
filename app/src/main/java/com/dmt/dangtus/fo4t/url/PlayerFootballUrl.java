package com.dmt.dangtus.fo4t.url;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PlayerFootballUrl {
    private static String urlBase = "https://top1elo.000webhostapp.com/player_football/";
//    private static String urlBase = "http://192.168.1.3/quanlicauthu/player_football/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String getUrlBase() {
        return urlBase;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(urlBase + url, params, responseHandler);
    }
}