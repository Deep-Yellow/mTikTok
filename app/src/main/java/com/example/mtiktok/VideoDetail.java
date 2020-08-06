package com.example.mtiktok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.mtiktok.adapter.VerticalViewPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.example.mtiktok.widget.VerticalViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
        urlList.add("https://jzvd.nathen.cn/video/2a101070-170bad88892-0007-1823-c86-de200.mp4");
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