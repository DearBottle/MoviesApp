package com.bottle.moviesapp.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.base.BaseActivity;
import com.bottle.moviesapp.base.BaseApplication;
import com.bottle.moviesapp.net.Config;

public class PersonCenterActivity extends BaseActivity {

    private Button btnLogout;

    private TextView tvName;
    private TextView tvLv;
    private TextView tvExp;


    @Override
    protected int getResId() {
        return R.layout.activity_person_center;
    }

    @Override
    protected void initView() {
        btnLogout = findViewById(R.id.btn_logout);
        tvName = findViewById(R.id.tv_name);
        tvLv = findViewById(R.id.tv_lv);
        tvExp = findViewById(R.id.tv_exp);
    }

    @Override
    protected void initData() {
        if (Config.getUserBean() != null) {
            tvName.setText(Config.getUserBean().getName());
            tvLv.setText("等级: LV" + Config.getUserBean().getLevel());
            tvExp.setText("经验: " + Config.getUserBean().getExp());
        }
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
