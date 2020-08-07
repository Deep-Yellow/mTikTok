package com.example.mtiktok;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;


import com.example.mtiktok.adapter.VerticalViewPagerAdapter;

import com.example.mtiktok.widget.ApiService;
import com.example.mtiktok.widget.VideoInfo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.example.mtiktok.widget.VerticalViewPager2;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class VideoDetail extends AppCompatActivity {
    @BindView(R.id.vvp_back_play)
    VerticalViewPager2 vvpBackPlay;
    @BindView(R.id.srl_page)
    SmartRefreshLayout srlPage;

    private List<String> urlList;
    private VerticalViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        initView();
        addListener();
    }
    private void addListener() {
        srlPage.setEnableAutoLoadMore(false);
        srlPage.setEnableLoadMore(false);
        srlPage.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                srlPage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        urlList.addAll(urlList);
                        pagerAdapter.setUrlList(urlList);
                        pagerAdapter.notifyDataSetChanged();

                        srlPage.finishLoadMore();
                    }
                }, 2000);

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }
    private void initView() {
        makeData();
        pagerAdapter = new VerticalViewPagerAdapter(getSupportFragmentManager());
//        vvpBackPlay.setVertical(true);
        vvpBackPlay.setOffscreenPageLimit(10);
        pagerAdapter.setUrlList(urlList);
        vvpBackPlay.setAdapter(pagerAdapter);

        vvpBackPlay.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == urlList.size() - 1) {
                    srlPage.setEnableAutoLoadMore(true);
                    srlPage.setEnableLoadMore(true);
                } else {
                    srlPage.setEnableAutoLoadMore(false);
                    srlPage.setEnableLoadMore(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void makeData() {
        urlList = new ArrayList<>();
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
                    }
                    pagerAdapter.setUrlList(urlList);
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<VideoInfo.Video>> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });
    }
//    private Button buttonPlay;
//    private Button buttonPause;
//    private VideoView videoView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_detail);
//        setTitle("VideoView");
//
//        buttonPause = findViewById(R.id.buttonPause);
//        buttonPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.pause();
//            }
//        });
//
//        buttonPlay = findViewById(R.id.buttonPlay);
//        buttonPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.start();
//            }
//        });
//
//        videoView = findViewById(R.id.videoView);
//        videoView.setVideoURI(Uri.parse("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4"));
//        videoView.start();
////        videoView.setVideoPath("");
//    }

}