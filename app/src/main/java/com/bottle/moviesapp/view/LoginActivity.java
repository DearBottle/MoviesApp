package com.bottle.moviesapp.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;

public class LoginActivity extends BaseActivity {


    private EditText etUserName;
    private EditText etPwd;
    private Button btnLogin;
    private TextView tvCopyrightName;

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

    @Override
    protected void initData() {

    }

    @Override
    protected void initClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMain();
            }
        });
    }

    /**
     * 跳到首頁
     */
    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
