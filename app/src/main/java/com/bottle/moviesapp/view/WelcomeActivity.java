package com.bottle.moviesapp.view;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {

    private ProgressBar progressBar;
    private TextView tvTimer;
    private CountDownTimer mTimer;

    @Override
    protected int getResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        progressBar = findViewById(R.id.progressBar);
        tvTimer = findViewById(R.id.tv_timer);
    }

    @Override
    protected void initData() {
        mTimer = new CountDownTimer(4000, 1000) {
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

}
