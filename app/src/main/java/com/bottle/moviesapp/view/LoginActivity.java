package com.bottle.moviesapp.view;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;
import com.bottle.moviesapp.bean.BaseRequset;
import com.bottle.moviesapp.bean.RequestUserToken;
import com.bottle.moviesapp.bean.UserBean;
import com.bottle.moviesapp.bean.UserTokenBean;
import com.bottle.moviesapp.net.AppApiManager;
import com.bottle.moviesapp.net.Config;
import com.bottle.moviesapp.net.DxResponse;
import com.bottle.moviesapp.net.Request;
import com.bottle.moviesapp.utils.PermissionUtil;
import com.bottle.moviesapp.utils.TextUtil;
import com.bottle.moviesapp.utils.ToastUtil;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class LoginActivity extends BaseActivity {


    private EditText etUserName;
    private EditText etPwd;
    private Button btnLogin;
    private TextView tvCopyrightName;
    private String[] permissionArr = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private PermissionUtil permissionUtil;

    @Override
    protected int getResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUserName = findViewById(R.id.et_user_name);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        tvCopyrightName = findViewById(R.id.tv_copyright_name);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        permissionUtil = new PermissionUtil(this, new PermissionUtil.RequsetPermissionsListener() {
            @Override
            public void onSuccess() {
            }
        });
        permissionUtil.checkPermissions(permissionArr);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startMain();
                login(etUserName.getText().toString(), etPwd.getText().toString());
            }
        });
    }


    public void login(final String loginName, final String pwd) {
        showWaiting("登录中...");
        Request.getInstant().doRequest(AppApiManager.getInstance().getApi()
                        .login(new RequestUserToken(loginName, pwd))
                        .subscribeOn(Schedulers.io())
                        .doOnNext(new Consumer<DxResponse<UserTokenBean>>() {
                            @Override
                            public void accept(DxResponse<UserTokenBean> tokenResponse) throws Exception {
                                if (!tokenResponse.isSucc()
                                        || tokenResponse.getData() == null
                                        || !TextUtil.isValidate(tokenResponse.getData().getToken())) {
                                    ToastUtil.showToast(LoginActivity.this, tokenResponse.getMessage());
                                    throw new Exception(tokenResponse.getMessage());
                                }

                                Config.setUserToken(tokenResponse.getData().getToken());
                                Config.saveLoginUser(loginName, pwd);
                            }
                        })
                        .flatMap(new Function<DxResponse<UserTokenBean>, Flowable<DxResponse<UserBean>>>() {
                            @Override
                            public Flowable<DxResponse<UserBean>> apply(DxResponse<UserTokenBean> userTokenBeanDxResponse) throws Exception {
                                return AppApiManager.getInstance().getApi().rbac_user(new BaseRequset());
                            }
                        }),
                new ResourceSubscriber<DxResponse<UserBean>>() {
                    @Override
                    public void onNext(DxResponse<UserBean> userResponse) {
                        hideWaiting();
                        if (!userResponse.isSucc() || userResponse.getData() == null) {
                        } else {
                            Config.setUserBean(userResponse.getData());
                            loginSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        hideWaiting();
                        ToastUtil.showToast(LoginActivity.this, "登录失败");
                    }

                    @Override
                    public void onComplete() {
                    }
                }

        );

    }

    private void loginSuccess() {
        startMain();
    }


    /**
     * 跳到首頁
     */
    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
