package com.example.mtiktok.fragment;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.mtiktok.R;
import com.example.mtiktok.widget.MyVideoPlayer;
import com.yqw.hotheart.HeartFrameLayout;
import com.yqw.hotheart.minterface.DoubleClickListener;

import java.lang.reflect.Field;

import butterknife.BindView;




public class VideoFragment extends BaseFragment {
    @BindView(R.id.txv_video)
    MyVideoPlayer txvVideo;
    @BindView(R.id.rl_back_right)
    RelativeLayout rlBackRight;
    @BindView(R.id.dl_back_play)
    DrawerLayout dlBackPlay;
    @BindView(R.id.heart)
    HeartFrameLayout heartFrameLayout;
    private String url;
    public static final String URL = "URL";

    @Override
    protected int getLayoutId() {
        return R.layout.fm_video;
    }

    @Override
    protected void initView() {
        heartFrameLayout.setOnDoubleClickListener(new DoubleClickListener() {
            @Override
            public void onDoubleClick(View view) {
                Log.d("lsn","双击了");
            }
        });
        url = getArguments().getString(URL);
        Glide.with(context)
                .load(url)
                .into(txvVideo.thumbImageView);
        txvVideo.rl_touch_help.setVisibility(View.GONE);
        txvVideo.setUp(url, url);

    }

    @Override
    protected void loadData() {
        txvVideo.startVideo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (txvVideo == null) {
            return;
        }
        if (isVisibleToUser) {
            txvVideo.goOnPlayOnResume();
        } else {
            txvVideo.goOnPlayOnPause();
        }

    }

    @Override
    public void onResume() {

        super.onResume();
        if (txvVideo != null) {
            txvVideo.goOnPlayOnResume();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (txvVideo != null) {
            txvVideo.goOnPlayOnPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (txvVideo != null) {
            txvVideo.releaseAllVideos();
        }
    }


    /**
     * 设置 全屏滑动
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    private void setDrawerRightEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field mRightDragger =
                    drawerLayout.getClass().getDeclaredField("mRightDragger");//Right
            mRightDragger.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) mRightDragger.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }
}
