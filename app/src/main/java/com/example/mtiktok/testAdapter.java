package com.example.mtiktok;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class testAdapter extends RecyclerView.Adapter<testAdapter.VideoViewHolder>{

    private static final String TAG = "testAdapter";
    private int mVideoItems;
    private static int viewHolderCount;

    public testAdapter(int mVideoItems) {
        this.mVideoItems = mVideoItems;
        viewHolderCount = 0;
    }


    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.image_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        VideoViewHolder videoViewHolder = new VideoViewHolder(view);

        videoViewHolder.imageView.setImageResource(R.mipmap.pic);

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return videoViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
    }

    @Override
    public int getItemCount() {
        return mVideoItems;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

//        private VideoView videoView;
//
//
//        public VideoViewHolder(@NonNull View itemView) {
//            super(itemView);
//            videoView = itemView.findViewById(R.id.videoView);
//        }
        private ImageView imageView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);

        }

    }
}
