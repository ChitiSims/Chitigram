package com.example.chitis.chitigram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private Button SignupBtn;
    private RelativeLayout rl;
    AnimationDrawable animationDrawable;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rl = findViewById(R.id.rlLogin);

        animationDrawable =(AnimationDrawable)rl.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        // onResume
        animationDrawable.start();


        usernameInput = findViewById(R.id.tlUsername);
        passwordInput = findViewById(R.id.tlPassword);
        loginBtn = findViewById(R.id.btLogin);
        SignupBtn = findViewById(R.id.btSignUp);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);
            }
        });

        SignupBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Intent j = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(j);
            }
        });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            final Intent intent = new Intent(LoginActivity.this, landingActivity.class);
            startActivity(intent);
            finish();

        } else {
            // show the signup or login screen

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }



    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful");

                    final Intent intent = new Intent(LoginActivity.this, landingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
