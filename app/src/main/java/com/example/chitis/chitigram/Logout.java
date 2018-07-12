package com.example.chitis.chitigram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.ParseUser;

public class Logout extends AppCompatActivity {

    private ImageView profilepic;
    private Button LogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        profilepic = findViewById(R.id.ivProfilePic);
        LogoutBtn = findViewById(R.id.btLogout);

        //

        LogoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // log user out
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                final Intent intent = new Intent(Logout.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
