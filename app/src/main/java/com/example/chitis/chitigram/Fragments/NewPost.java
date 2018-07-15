package com.example.chitis.chitigram.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chitis.chitigram.R;
import com.example.chitis.chitigram.landingActivity;
import com.example.chitis.chitigram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class NewPost extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final String APP_TAG = "MyCustomApp";
    private static String imagePath;
    private EditText descriptionInput;
    private Button createButton;
    private ImageView picture;
    public String photoFileName = "photo.jpg";
    File photoFile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_new_post, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        descriptionInput = view.findViewById(R.id.etDescription);
        createButton = view.findViewById(R.id.btCreate);
        picture = view.findViewById(R.id.ivPicture);

        ImageView img = (ImageView) view.findViewById(R.id.ivPicture);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                dispatchTakePictureIntent();

            }
        });



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                final File file = new File(String.valueOf(photoFile));
                final ParseFile parseFile = new ParseFile(file);
                Toast.makeText(getActivity(), "created", Toast.LENGTH_LONG).show();

                createPost(description, parseFile, user);

                final Intent intent = new Intent(getActivity(), landingActivity.class);
                startActivity(intent);
            }
        });


    }


    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success!");
                } else {
                    e.printStackTrace();
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

            picture.setImageBitmap(bitmap);



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
