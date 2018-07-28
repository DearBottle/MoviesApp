package com.bottle.moviesapp.view;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.adapter.MoviesAdapter;
import com.bottle.moviesapp.base.BaseActivity;
import com.bottle.moviesapp.bean.MoviesBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView ivQq;
    private TextView tvPersonCenter;
    private TextView tvOfficial;
    private RecyclerView mRecyclerView;


    @Override
    protected int getResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recycler);
        ivQq = findViewById(R.id.iv_qq);
        tvPersonCenter = findViewById(R.id.tv_person_center);
        tvOfficial = findViewById(R.id.tv_official);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        mRecyclerView.setAdapter(moviesAdapter);

        List<MoviesBean> moviesBeans = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            MoviesBean bean = new MoviesBean("蜘蛛侠" + i, 100, 100);
            moviesBeans.add(bean);
        }
        moviesAdapter.addData(moviesBeans);
    }

    @Override
    protected void initClick() {
        ivQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QQActivity.class));
            }
        });
        tvPersonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PersonCenterActivity.class));
            }
        });
        tvOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OfficialActivity.class));
            }
        });
    }
}
