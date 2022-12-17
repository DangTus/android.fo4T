package com.dmt.dangtus.fo4t.url;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PlayerFootballUrl {
    private static String urlBase = "http://top1elo.lovestoblog.com/quanlicauthu/player_football/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String getUrlBase() {
        return urlBase;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Cookie", Cookie.get);
        client.get(urlBase + url, params, responseHandler);
    }
}