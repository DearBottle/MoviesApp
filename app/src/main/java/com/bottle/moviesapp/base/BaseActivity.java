package com.bottle.moviesapp.base;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;

import com.bottle.moviesapp.widget.DxLoadingDialog;

/**
 * Created by mengbaobao on 2018/7/24.
 */

public abstract class BaseActivity extends AppCompatActivity {

    //加载对话框
    private DxLoadingDialog mLoadingDialog;

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

    /**
     * 显示加载对话框
     */
    @UiThread
    public void showWaiting(String msg) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new DxLoadingDialog(this);
        }
        mLoadingDialog.show(msg);
    }

    /**
     * 隐藏加载对话框
     */
    @UiThread
    public void hideWaiting() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog = null;
        }
        BaseApplication.removeActivity(this);
        super.onDestroy();
    }
}
