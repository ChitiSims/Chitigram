package com.example.chitis.chitigram;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chitis.chitigram.Fragments.Home;
import com.example.chitis.chitigram.Fragments.NewPost;
import com.example.chitis.chitigram.Fragments.UserProfile;

public class landingActivity extends AppCompatActivity {
    Toolbar toolbar;
    Toolbar profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);


        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment timeline = new Home();
        final Fragment newPost = new NewPost();
        final Fragment profile = new UserProfile();

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flcontainer, timeline).commit();
                        getSupportActionBar().show();
                        return true;
                    case R.id.action_newPost:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flcontainer, newPost).commit();
                        getSupportActionBar().hide();
                        return true;
                    case R.id.action_logout:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flcontainer, profile).commit();
                        getSupportActionBar().hide();
                        return true;
                }
                return false;
            }
        };

        // Find the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
//        getSupportActionBar().setLogo(R.drawable.ic_nav_logo);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if  (getIntent().hasExtra("post")) {
            navigation.setSelectedItemId(R.id.action_logout);
        }else{
        navigation.setSelectedItemId(R.id.action_home); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}