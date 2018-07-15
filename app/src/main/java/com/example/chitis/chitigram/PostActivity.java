package com.example.chitis.chitigram;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chitis.chitigram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class PostActivity extends AppCompatActivity {
    private Image image;
    private ImageView ivImage;
    private TextView tvDescription;
    private SwipeRefreshLayout swipeContainer;
    InstaAdapter instaAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //   find the recyclerView
        rvPosts = (RecyclerView) findViewById(R.id.rvPost);

        posts = new ArrayList<>();
        // construct the adapter from this datasource
        instaAdapter = new InstaAdapter(posts);
        // RecyclerView setup (layout manager, user adapter)
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        //  set the adapter
        rvPosts.setAdapter(instaAdapter);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // do something here
                        final Intent intent = new Intent(getBaseContext(), PostActivity.class);
                        startActivity(intent);


                        Toast.makeText(PostActivity.this, "home selected", Toast.LENGTH_SHORT).show();
                        //set color to black
                        return true;
                    case R.id.action_newPost:
                        // do something here
                        final Intent i = new Intent(getBaseContext(), HomeActivity.class);
                        startActivity(i);

                        //set color to black

                        Toast.makeText(PostActivity.this, "new photo selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_logout:
                        // do something here
                        final Intent j = new Intent(getBaseContext(), Logout.class);
                        startActivity(j);

                        //set color to black
                        Toast.makeText(PostActivity.this, "logout selected", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.
                setOnRefreshListener
                        (new SwipeRefreshLayout.
                                OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                // Your code to refresh the list here.
                                // Make sure you call swipeContainer.setRefreshing(false)
                                // once the network request has completed successfully.
                                getPosts();

                            }
                        });

        getPosts();
        // Specify which class to query

    }

    public void getPosts(){
        final Post.Query query = new Post.Query();
        query.getTop().withUser();
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    posts.clear();
                    instaAdapter.addAll(objects);
                    swipeContainer.setRefreshing(false);
                } else {
                    e.printStackTrace();
                    Log.e("FeedActivity", "Failed to get posts");
                }
            }
        });
    }




}
