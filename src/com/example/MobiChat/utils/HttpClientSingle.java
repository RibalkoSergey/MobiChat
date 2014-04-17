package com.example.MobiChat.utils;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/13/14
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientSingle {
    private static DefaultHttpClient uniqInstance;

    private HttpClientSingle() {

    }

    public static DefaultHttpClient getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new DefaultHttpClient();
        }
        return uniqInstance;
    }
}