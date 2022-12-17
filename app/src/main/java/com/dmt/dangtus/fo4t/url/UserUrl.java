package com.dmt.dangtus.fo4t.url;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserUrl {
    private static String urlBase = "http://top1elo.lovestoblog.com/quanlicauthu/user/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static String getUrlBase() {
        return urlBase;
    }

    public static void checkAccount(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Cookie", Cookie.get);
        client.post(urlBase + url, params, responseHandler);
    }

    public static void createNew(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Cookie", Cookie.get);
        client.post(urlBase + url, params, responseHandler);
    }

    public static void getByID(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Cookie", Cookie.get);
        client.get(urlBase + url, params, responseHandler);
    }

    public static void edit(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Cookie", Cookie.get);
        client.post(urlBase + url, params, responseHandler);
    }
}
