package com.bottle.moviesapp.view;

import android.view.View;
import android.widget.ImageView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.bean.MoviesBean;
import com.bottle.moviesapp.exo.GSYExo2PlayerView;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;


/**
 * 使用自定义的ExoPlayer，实现无缝切换下一集功能
 */
public class ViedoActivity extends GSYBaseActivityDetail<GSYExo2PlayerView> {

    private GSYExo2PlayerView detailPlayer;
    private MoviesBean moviesBean;

    @Override
    protected int getResId() {
        return R.layout.activity_viedo;
    }

    @Override
    protected void initView() {
        moviesBean = (MoviesBean) getIntent().getSerializableExtra("data");
        detailPlayer = findViewById(R.id.detail_player);
        //GSYBaseActivityDetail 的 普通模式初始化
        initVideo();
    }

    @Override
    protected void initData() {
        List<GSYVideoModel> urls = new ArrayList<>();
        urls.add(new GSYVideoModel(moviesBean.getPlayPath(), moviesBean.getTitle()));
        detailPlayer.setUp(urls, 0);
        //使用 exo 的 CacheDataSourceFactory 实现
        detailPlayer.setExoCache(true);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        detailPlayer.setThumbImageView(imageView);
        resolveNormalVideoUI();
        detailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        detailPlayer.setRotateViewAuto(false);
        detailPlayer.setLockLand(false);
        detailPlayer.setShowFullAnimation(false);
        detailPlayer.setNeedLockFull(true);
        detailPlayer.setVideoAllCallBack(this);
        //全屏
        showFull();
        clickForFullScreen();
    }

    @Override
    protected void initClick() {
       /* next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GSYExoVideoManager.instance().next();
            }
        });*/
        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });

    }


    /**
     * 重载为GSYExoVideoManager的方法处理
     */
    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
       /* if (GSYExoVideoManager.backFromWindowFull(this)) {
            return;
        }*/
        super.onBackPressed();
    }


    @Override
    public GSYExo2PlayerView getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //不用builder的模式
        return null;
    }

    @Override
    public void clickForFullScreen() {

    }

    /**
     * 是否启动旋转横屏，true表示启动
     *
     * @return true
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }


    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
    }

    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }
}
