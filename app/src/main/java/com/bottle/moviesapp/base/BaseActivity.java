package com.bottle.moviesapp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mengbaobao on 2018/7/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        BaseApplication.addActivity(this);
        initView();
        initData();
        initClick();
    }

    protected abstract int getResId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initClick();

    @Override
    protected void onDestroy() {
        BaseApplication.removeActivity(this);
        super.onDestroy();
    }
}
