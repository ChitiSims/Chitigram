package com.example.chitis.chitigram;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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



// Specify the object id
//        query.findInBackground(new FindCallback<Post>() {
//            public void done(List<Post> itemList, ParseException e) {
//                if (e == null) {
//                    // Access the array of results here
//                    String firstItemId = itemList.get(0).getObjectId();
//                    posts = (ArrayList<Post>) itemList;
//                    e
//
//                    Toast.makeText(PostActivity.this, firstItemId, Toast.LENGTH_SHORT).show();
//                } else {
//                    Log.d("item", "Error: " + e.getMessage());
//                }
//            }
//        });
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
