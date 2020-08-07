package com.example.mtiktok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;

import com.example.mtiktok.adapter.ListVideoAdapter;
import com.example.mtiktok.widget.ApiService;
import com.example.mtiktok.widget.MyVideoPlayer;
import com.example.mtiktok.widget.VideoInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VideoList extends AppCompatActivity {
    private ArrayList<String> urlList;
    private ArrayList<String> desList;
    private RecyclerView rvList;
    private ListVideoAdapter videoAdapter;
    private int firstVisibleItem;
    private int lastVisibleItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        rvList = findViewById(R.id.rv_list);
        urlList = new ArrayList<>();
        desList = new ArrayList<>();
        videoAdapter = new ListVideoAdapter(urlList);
        rvList.setLayoutManager(new LinearLayoutManager(VideoList.this));
        rvList.setAdapter(videoAdapter);
        addListener();
        getData();
    }

    private void getData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideos().enqueue(new Callback<ArrayList<VideoInfo.Video>>() {
            @Override
            public void onResponse(Call<ArrayList<VideoInfo.Video>> call, Response<ArrayList<VideoInfo.Video>> response) {
                if(response.body()!=null){
                    ArrayList<VideoInfo.Video> videos = response.body();
                    Log.d("eeee",videos.toString());
                    for(VideoInfo.Video v :  videos){
                        Log.d("ffff",v.toString());
                        urlList.add("https"+v.feedurl.substring(4));
                        desList.add(v.description);
                    }
                    videoAdapter.setData(urlList);
                    videoAdapter.setDes(desList);
                    videoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VideoInfo.Video>> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }

    private void addListener() {


        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        /**在这里执行，视频的自动播放与停止*/
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        MyVideoPlayer.releaseAllVideos();
                        break;
                }

            }
        });
    }

    /**
     * 自动播放
     */
    private void autoPlayVideo(RecyclerView recyclerView) {
        if (firstVisibleItem == 0 && lastVisibleItem == 0 && recyclerView.getChildAt(0) != null) {

            MyVideoPlayer videoView = null;
            if (recyclerView != null && recyclerView.getChildAt(0) != null) {
                videoView = recyclerView.getChildAt(0).findViewById(R.id.mp_video);
            }
            if (videoView != null) {
                if (videoView.state == MyVideoPlayer.STATE_NORMAL || videoView.state == MyVideoPlayer.STATE_PAUSE) {
                    videoView.startVideo();
                }
            }
        }

        for (int i = 0; i <= lastVisibleItem; i++) {
            if (recyclerView == null || recyclerView.getChildAt(i) == null) {
                return;
            }


            MyVideoPlayer
                    videoView = recyclerView.getChildAt(i).findViewById(R.id.mp_video);
            if (videoView != null) {

                Rect rect = new Rect();
                //获取视图本身的可见坐标，把值传入到rect对象中
                videoView.getLocalVisibleRect(rect);
                //获取视频的高度
                int videoHeight = videoView.getHeight();

                if (rect.top <= 100 && rect.bottom >= videoHeight) {
                    if (videoView.state == MyVideoPlayer.STATE_NORMAL || videoView.state == MyVideoPlayer.STATE_PAUSE) {
                        videoView.startVideo();
                    }
                    return;
                }

                MyVideoPlayer.releaseAllVideos();

            } else {
                MyVideoPlayer.releaseAllVideos();
            }

        }

    }

    @Override
    public void onPause() {
        super.onPause();
        MyVideoPlayer.releaseAllVideos();
    }



//    class ListVideoAdapter extends BaseRecAdapter<String, VideoViewHolder> {
//
//
//        public ListVideoAdapter(List<String> list) {
//            super(list);
//        }
//
//
//
//        @Override
//        public void onHolder(VideoViewHolder holder, String bean, int position) {
//            holder.mp_video.setUp(bean, "第" + position + "个视频", MyVideoPlayer.STATE_NORMAL);
//            if (position == 0) {
//                holder.mp_video.startVideo();
//            }
//            Glide.with(context).load(bean).into(holder.mp_video.thumbImageView);
//            holder.tv_title.setText("第" + position + "个视频");
//        }
//
//        @Override
//        public VideoViewHolder onCreateHolder() {
//            return new VideoViewHolder(getViewByRes(R.layout.item_video));
//
//        }
//
//
//    }
//
//    public class VideoViewHolder extends BaseRecViewHolder {
//        public View rootView;
//        public MyVideoPlayer mp_video;
//        public TextView tv_title;
//
//        public VideoViewHolder(View rootView) {
//            super(rootView);
//            this.rootView = rootView;
//            this.mp_video = rootView.findViewById(R.id.mp_video);
//            this.tv_title = rootView.findViewById(R.id.tv_title);
//        }
//
//    }

}