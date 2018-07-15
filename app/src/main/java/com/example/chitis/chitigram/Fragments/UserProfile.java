package com.example.chitis.chitigram.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chitis.chitigram.InstaAdapter2;
import com.example.chitis.chitigram.LoginActivity;
import com.example.chitis.chitigram.R;
import com.example.chitis.chitigram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UserProfile extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView ivPropic;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private TextView tvPosts;
    private TextView username;
    private RecyclerView rvGrid;
    private InstaAdapter2 mAdapter;
    private List<Post> posts;
    private Post post1;
    private Button logout;
    public String photoFileName = "photo.jpg";
    File photoFile;
    public final String APP_TAG = "MyCustomApp";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment

        return inflater.inflate(R.layout.fragment_user_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        tvPosts = view.findViewById(R.id.tvPosts);
        ivPropic = view.findViewById(R.id.ivProPics);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        username = view.findViewById(R.id.tvUsernames);
        logout = view.findViewById(R.id.btLogout);

        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // log user out
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                final Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ParseUser user = ParseUser.getCurrentUser();

        Parcelable parcel = getActivity().getIntent().getParcelableExtra("post");
        post1 = (Post) Parcels.unwrap(parcel);

        ivPropic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        tvFollowers.setText("0");
        tvFollowing.setText("2");
        if (post1 == null) {
            username.setText(user.getString("username"));
        } else {
        username.setText(post1.getUser().getString("username"));
        }
        // Find RecyclerView and bind to adapter
        rvGrid = (RecyclerView) view.findViewById(R.id.rvGrid);

        // allows for optimizations
        rvGrid.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(getActivity(), 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvGrid.setLayoutManager(layout);

        // get data
        posts = new ArrayList<>();

        // Create an adapter
        mAdapter = new InstaAdapter2(posts);

        // Bind adapter to list
        rvGrid.setAdapter(mAdapter);
        getPosts();
        //tvPosts.setText(posts.size());

    }
    public void getPosts(){
        final Post.Query query = new Post.Query();
        query.getTop().withUser();
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    posts.clear();
                   // mAdapter.addAll(objects);
                    for (int i = 0; i < objects.size(); i++){
                        try {
                            if (objects.get(i).getUser().fetchIfNeeded().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                                posts.add(objects.get(i));
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    e.printStackTrace();
                    Log.e("FeedActivity", "Failed to get posts");
                }
            }
        });
    }
    

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getActivity(), "com.example.chitis.chitigram", photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            String photoFileName = photoFile.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(photoFileName);

            ivPropic.setImageBitmap(bitmap);



        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }
}