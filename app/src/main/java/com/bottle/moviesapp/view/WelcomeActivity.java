package com.bottle.moviesapp.view;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;
import com.bottle.moviesapp.bean.AdvertAddBean;
import com.bottle.moviesapp.bean.AdvertBean;
import com.bottle.moviesapp.bean.BaseRequset;
import com.bottle.moviesapp.net.AppApiManager;
import com.bottle.moviesapp.net.DxResponse;
import com.bottle.moviesapp.net.Request;
import com.bottle.moviesapp.utils.PreferenceUtil;
import com.bottle.moviesapp.utils.TextUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class WelcomeActivity extends BaseActivity {

    private TextView tvTimer;
    private CountDownTimer mTimer;
    private ImageView ivHorn;

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        tvTimer = findViewById(R.id.tv_timer);
        ivHorn = findViewById(R.id.iv_horn);

    }

    @Override
    protected void initData() {
        showAdvert(0, null, null);
        getAdvert();

        mTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText(l / 1000 + "");
            }

            @Override
            public void onFinish() {
                startLogin();
            }
        }.start();
    }

    @Override
    protected void initClick() {
        tvTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });
    }

    /**
     * 跳到登录页
     */
    private void startLogin() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void getAdvert() {
        Request.getInstant().doRequest(AppApiManager.getInstance().getApi()
                        .getAdvert(new BaseRequset())
                        .subscribeOn(Schedulers.io()),
                new ResourceSubscriber<DxResponse<List<AdvertBean>>>() {
                    @Override
                    public void onNext(final DxResponse<List<AdvertBean>> permissionBeanDxResponse) {
                        if (permissionBeanDxResponse == null || !TextUtil.isValidate(permissionBeanDxResponse.getData())) {
                            return;
                        }
                        showAdvert(permissionBeanDxResponse.getData().get(0).getId(),
                                permissionBeanDxResponse.getData().get(0).getImgUrl(),
                                permissionBeanDxResponse.getData().get(0).getUrl());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("44", "dd");
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }

    private void showAdvert(int id, String ImgUrl, String url) {
        if (TextUtil.isValidate(ImgUrl) && TextUtil.isValidate(url)) {
            PreferenceUtil.putString("ImgUrl", ImgUrl);
            PreferenceUtil.putInt("Id", id);
            PreferenceUtil.putString("Url", url);
        } else {
            id = PreferenceUtil.getInt("Id", 0);
            ImgUrl = PreferenceUtil.getString("ImgUrl", "");
            url = PreferenceUtil.getString("Url", "");
        }
        if (id == 0) {
            return;
        }
        Glide.with(WelcomeActivity.this)
                .load(ImgUrl)
                .into(ivHorn);

        final String finalUrl = url;
        final int finalId = id;
        ivHorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpAdvert(finalId, finalUrl);
            }
        });
    }

    private void jumpAdvert(int id, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uri);
        startActivity(intent);
        Request.getInstant().doRequest(AppApiManager.getInstance().getApi()
                        .addAdvert(new AdvertAddBean(id))
                        .subscribeOn(Schedulers.io()),
                new ResourceSubscriber<DxResponse>() {
                    @Override
                    public void onNext(DxResponse response) {
                        if (response != null) {

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t != null) {

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                }
        );
    }
}
