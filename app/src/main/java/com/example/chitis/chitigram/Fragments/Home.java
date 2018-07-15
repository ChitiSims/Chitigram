package com.example.chitis.chitigram.Fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chitis.chitigram.Decor.SimpleDividerItemDecoration;
import com.example.chitis.chitigram.InstaAdapter;
import com.example.chitis.chitigram.R;
import com.example.chitis.chitigram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    private Image image;
    private ImageView ivImage;
    private TextView tvDescription;
    private SwipeRefreshLayout swipeContainer;
    InstaAdapter instaAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        return inflater.inflate(R.layout.fragment_timeline, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        //   find the recyclerView
        rvPosts = (RecyclerView) view.findViewById(R.id.rvPost);

        posts = new ArrayList<>();
        // construct the adapter from this datasource
        instaAdapter = new InstaAdapter(posts);
        // RecyclerView setup (layout manager, user adapter)
        rvPosts.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //  set the adapter
        rvPosts.setAdapter(instaAdapter);


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
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

        rvPosts.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

    }



    public void getPosts(){
        final Post.Query query = new Post.Query();
        query.getTop().withUser().orderByDescending("createdAt").findInBackground(new FindCallback<Post>() {
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