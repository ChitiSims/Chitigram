package com.example.chitis.chitigram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("chitigramID")
                .clientKey("chitiKey")
                .server("http://chitigram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);

    }
}
