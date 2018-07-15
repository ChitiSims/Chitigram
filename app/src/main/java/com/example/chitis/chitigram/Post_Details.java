package com.example.chitis.chitigram;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chitis.chitigram.model.Post;
import com.example.chitis.chitigram.model.TimeFormatter;

import org.parceler.Parcels;

public class Post_Details extends AppCompatActivity {

    private ImageView propic;
    private TextView username;
    private TextView description;
    private ImageView post;
    private Post post1;
    private TextView time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__details);

        Parcelable parcel = getIntent().getParcelableExtra("post");
        post1 = (Post) Parcels.unwrap(parcel);

        propic = findViewById(R.id.ivpic);
        username = findViewById(R.id.tvUser);
        description = findViewById(R.id.tvDes);
        post = findViewById(R.id.ivPost);
        time = findViewById(R.id.tvTime);

        username.setText(post1.getUser().getString("username"));
        description.setText(post1.getDescription());
        time.setText(TimeFormatter.getTimeDifference(post1.getCreatedAt().toString()));

        Glide.with(this).load(post1.getImage().getUrl()).into(post);
    }
}
