package com.example.chitis.chitigram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chitis.chitigram.model.Post;

import java.util.List;

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {
    private List<Post> mPosts;
    Context context;
    private final int REQUEST_CODE = 21;
    private final int REQUEST_CODE_1 = 22;
    // pass in the Tweets array in the constructor
    public InstaAdapter(List<Post> posts) {
        mPosts = posts;

    }
    // for reach row, inflate the layout and cache references into ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }


    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data according to position
        final Post post = mPosts.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(post.getUser().getString("username"));
        holder.tvDescription.setText(post.getDescription());


        Glide.with(context).load(post.getImage().getUrl()).into(holder.ivPost);

//
//        holder.tvBody.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(context, DetailActivity.class);
//                i.putExtra("tweet", Parcels.wrap(tweet));
//                ((Activity) context).startActivity(i);
//                Toast.makeText(context, tweet.body, Toast.LENGTH_SHORT).show();
//            }
//        });


//        holder.reply.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent i = new Intent(context, ReplyActivity.class);
//                i.putExtra("username", Parcels.wrap(tweet));
//                ((Activity) context).startActivityForResult(i, REQUEST_CODE);
//                Toast.makeText(context, tweet.user.name, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();

    }

    // create the ViewHolder class

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPost;
        public TextView tvUsername;
        public TextView tvDescription;
        //public ImageButton reply;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            //reply =(ImageButton) itemView.findViewById(R.id.ibReply);
            ivPost = (ImageView) itemView.findViewById(R.id.ivPost);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
        }



    }


}
