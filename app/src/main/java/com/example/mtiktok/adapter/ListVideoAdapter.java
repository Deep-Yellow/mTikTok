package com.example.mtiktok.adapter;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mtiktok.R;
import com.example.mtiktok.listToShow.BaseRecAdapter;
import com.example.mtiktok.listToShow.BaseRecViewHolder;
import com.example.mtiktok.widget.MyVideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class ListVideoAdapter extends BaseRecAdapter<String, VideoViewHolder> {

    private ArrayList<String> desc;
    public ListVideoAdapter(List<String> list) {
        super(list);
        desc = new ArrayList<>();
    }
    public void setDes(ArrayList<String> a){this.desc=a;}

    @Override
    public void onHolder(VideoViewHolder holder, String bean, int position) {
        holder.mp_video.setUp(bean, "第" + position + "个视频", MyVideoPlayer.STATE_NORMAL);
        if (position == 0) {
            holder.mp_video.startVideo();
        }
        Glide.with(context).load(bean).into(holder.mp_video.thumbImageView);
        holder.tv_title.setText("第" + position + "个视频");
        holder.desc.setText(desc.get(position));
    }

    @Override
    public VideoViewHolder onCreateHolder() {
        return new VideoViewHolder(getViewByRes(R.layout.item_video));

    }


}

class VideoViewHolder extends BaseRecViewHolder {
    public View rootView;
    public MyVideoPlayer mp_video;
    public TextView tv_title;
    public TextView desc;
    public VideoViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.mp_video = rootView.findViewById(R.id.mp_video);
        this.tv_title = rootView.findViewById(R.id.tv_title);
        this.desc = rootView.findViewById(R.id.description);
    }

}
