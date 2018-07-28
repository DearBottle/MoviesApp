package com.bottle.moviesapp.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;
import com.bottle.moviesapp.base.BaseApplication;

public class PersonCenterActivity extends BaseActivity {

    private Button btnLogout;


    @Override
    protected int getResId() {
        return R.layout.activity_person_center;
    }

    @Override
    protected void initView() {
        btnLogout = findViewById(R.id.btn_logout);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initClick() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseApplication.logout();
                startActivity(new Intent(PersonCenterActivity.this, LoginActivity.class));
            }
        });
    }
}
