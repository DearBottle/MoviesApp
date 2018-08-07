package com.bottle.moviesapp.view;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
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
import com.bottle.moviesapp.utils.VideoFormat;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView ivQq;
    private TextView tvPersonCenter;
    private TextView tvOfficial;
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<MoviesBean> videoRows;


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
        moviesAdapter = new MoviesAdapter();
        mRecyclerView.setAdapter(moviesAdapter);


        List<MoviesBean> moviesBeans = getMovies();
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
        moviesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                VideoFormat.encrypt(videoRows.get(position).getFilePath());
                Intent intent = new Intent(MainActivity.this, ViedoActivity.class);
                intent.putExtra("data", videoRows.get(position));
                startActivity(intent);
            }
        });
    }

    public ArrayList<MoviesBean> getMovies() {
        //从MediaStore.Video.Thumbnail查询中获得的列的列表。
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};
        //从MediaStore.Video.Media查询中获得的列的列表。
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.MIME_TYPE};
        // 在主查询中将选择所有在MediaStore中表示的视频
        Cursor cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);
        videoRows = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                MoviesBean newVVI = new MoviesBean();
                //将使用另一个查询为每个视频提取缩略图，而且这些数据块都将存储在VideoViewInfo对象中。
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = managedQuery(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    newVVI.setThumbPath(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                newVVI.setFilePath(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                newVVI.setTitle(cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));
                newVVI.setMimeType(cursor
                        .getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));
                videoRows.add(newVVI);
            } while (cursor.moveToNext());
        }

        return videoRows;
    }
}
