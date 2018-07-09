package com.example.chitis.chitigram;

import android.app.Application;

import com.example.chitis.chitigram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("chitigramID")
                .clientKey("chitiKey")
                .server("http://chitigram.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);

    }
}
