package com.bottle.moviesapp.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.adapter.MoviesAdapter;
import com.bottle.moviesapp.base.BaseActivity;
import com.bottle.moviesapp.bean.ApplyPermissionBean;
import com.bottle.moviesapp.bean.MoviesBean;
import com.bottle.moviesapp.bean.PayBean;
import com.bottle.moviesapp.bean.PermissionBean;
import com.bottle.moviesapp.bean.RequsetPermission;
import com.bottle.moviesapp.net.AppApiManager;
import com.bottle.moviesapp.net.Config;
import com.bottle.moviesapp.net.DxResponse;
import com.bottle.moviesapp.net.Request;
import com.bottle.moviesapp.utils.CProgressDialogUtils;
import com.bottle.moviesapp.utils.TextUtil;
import com.bottle.moviesapp.utils.ToastUtil;
import com.bottle.moviesapp.utils.VideoFormat;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainActivity extends BaseActivity {

    private ImageView ivQq;
    private TextView tvPersonCenter;
    private TextView tvOfficial;
    private RecyclerView mRecyclerView;
    private MoviesAdapter moviesAdapter;
    private ArrayList<MoviesBean> videoRowsAll;
    private ArrayList<MoviesBean> videoRows;
    private EditText et_search;
    private boolean testPlay = false;
    private ImageView ivRefresh;


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
        et_search = findViewById(R.id.et_search);
        ivRefresh = findViewById(R.id.iv_refresh);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        moviesAdapter = new MoviesAdapter();
        mRecyclerView.setAdapter(moviesAdapter);
        videoRowsAll = new ArrayList<>();

        //List<MoviesBean> moviesBeans = getMovies();
        //moviesAdapter.setNewData(moviesBeans);

        getMovies2(new File(Environment.getExternalStorageDirectory(), "BaiduNetdisk"));
        videoRows = videoRowsAll;
        moviesAdapter.setNewData(videoRows);

        moviesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()

        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (testPlay) {
                    play(videoRows.get(position));
                } else {
                    CProgressDialogUtils.showProgressDialog(MainActivity.this, "正在校验视频文件");
                    getSHA256(videoRows.get(position));
                    CProgressDialogUtils.cancelProgressDialog();
                    checkHasPay(videoRows.get(position));
                }
            }
        });
    }

    @Override
    protected void initClick() {
        ivQq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(MainActivity.this, QQActivity.class));
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=3389923020";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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
                Uri uri = Uri.parse(Config.url);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
               /* Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra(WebActivity.URL, Config.url);
                startActivity(intent);*/
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String txt = et_search.getText().toString();
                videoRows = new ArrayList<>();
                if (!TextUtil.isValidate(txt) || !TextUtil.isValidate(videoRowsAll)) {
                    videoRows = videoRowsAll;
                } else {
                    for (MoviesBean bean : videoRowsAll) {
                        if (bean.getTitle() != null && bean.getTitle().toLowerCase().contains(txt.toLowerCase()))
                            videoRows.add(bean);
                    }
                }
                moviesAdapter.setNewData(videoRows);
                moviesAdapter.notifyDataSetChanged();
            }
        });
        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
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
        videoRowsAll = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)).toLowerCase().contains("magic")) {
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
                    videoRowsAll.add(newVVI);
                }
            } while (cursor.moveToNext());
        }
        videoRows = videoRowsAll;
        return videoRowsAll;
    }

    /**
     * //搜索目录，扩展名，是否进入子文件夹
     */

    public void getMovies2(File file) {
        String Extension = "magic";
        if (!file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isFile()) {
                Log.e("mengbaobao", f.getName());
                if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension)) {
                    MoviesBean moviesBean = new MoviesBean();
                    moviesBean.setFilePath(f.getPath());
                    moviesBean.setTitle(f.getName());
                    videoRowsAll.add(moviesBean);
                }
            } else if (f.isDirectory() && f.getPath().indexOf("/.") == -1) {
                getMovies2(f);
            }
        }
    }


    public void getSHA256(final MoviesBean moviesBean) {
        if (TextUtil.isValidate(moviesBean.getSha256())) {
            return;
        }
        String sha256 = "";
        try {
            InputStream fis = new FileInputStream(moviesBean.getFilePath());
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;//解释参见最下方
                if (val < 16) {
                    hexValue.append("0");
                }
                //这里借助了Integer类的方法实现16进制的转换
                hexValue.append(Integer.toHexString(val));
            }
            sha256 = hexValue.toString();
            moviesBean.setSha256(sha256);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证购买
     */
    private void checkHasPay(final MoviesBean moviesBean) {
        CProgressDialogUtils.showProgressDialog(MainActivity.this, "正在验证用户权限");
        Request.getInstant().doRequest(AppApiManager.getInstance().getApi()
                        .checkPay(new RequsetPermission(moviesBean.getTitle().replace("magic", ""), moviesBean.getSha256()))
                        .subscribeOn(Schedulers.io()),
                new ResourceSubscriber<DxResponse<PayBean>>() {
                    @Override
                    public void onNext(DxResponse<PayBean> permissionBeanDxResponse) {
                        CProgressDialogUtils.cancelProgressDialog();

                        if (permissionBeanDxResponse == null) {
                            ToastUtil.showToast(MainActivity.this, "未知异常,请联系管理员");
                            return;
                        }
                        if (permissionBeanDxResponse.getCode() != 0 || permissionBeanDxResponse.getData() == null) {
                            ToastUtil.showToast(MainActivity.this, permissionBeanDxResponse.getMessage() + "");
                            return;
                        }
                        if (!permissionBeanDxResponse.getData().isPay()) {
                            Intent intent = new Intent(MainActivity.this, WebActivity.class);
                            intent.putExtra(WebActivity.URL, Config.videoDetail + permissionBeanDxResponse.getData().getId());
                            startActivity(intent);
                            return;
                        }
                        checkHasPermission(moviesBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        CProgressDialogUtils.cancelProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        CProgressDialogUtils.cancelProgressDialog();
                    }
                }
        );

    }

    /**
     * 验证用户授权
     */
    private void checkHasPermission(final MoviesBean moviesBean) {
        CProgressDialogUtils.showProgressDialog(MainActivity.this, "正在验证授权");
        Request.getInstant().doRequest(AppApiManager.getInstance().getApi()
                        .checkPermission(new RequsetPermission(moviesBean.getTitle(), moviesBean.getSha256()))
                        .subscribeOn(Schedulers.io()),
                new ResourceSubscriber<DxResponse<PermissionBean>>() {
                    @Override
                    public void onNext(DxResponse<PermissionBean> permissionBeanDxResponse) {
                        CProgressDialogUtils.cancelProgressDialog();
                        if (permissionBeanDxResponse == null) {
                            ToastUtil.showToast(MainActivity.this, "未知异常,请联系管理员");
                            return;
                        }
                        if (permissionBeanDxResponse.getCode() != 0 || permissionBeanDxResponse.getData() == null) {
                            ToastUtil.showToast(MainActivity.this, permissionBeanDxResponse.getMessage() + "");
                            return;
                        }
                        if (!permissionBeanDxResponse.getData().isAllow()) {
                            permissionAllow(moviesBean);
                            return;
                        }
                        play(moviesBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        CProgressDialogUtils.cancelProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        CProgressDialogUtils.cancelProgressDialog();
                    }
                }
        );

    }

    private void permissionAllow(final MoviesBean moviesBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("是否申请授权播放");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                applyPermission(moviesBean);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    /**
     * 申请授权
     */
    private void applyPermission(final MoviesBean moviesBean) {
        CProgressDialogUtils.showProgressDialog(MainActivity.this, "正在授权");
        Request.getInstant().doRequest(AppApiManager.getInstance().getApi()
                        .applyPermission(new RequsetPermission(moviesBean.getTitle(), moviesBean.getSha256()))
                        .subscribeOn(Schedulers.io()),
                new ResourceSubscriber<DxResponse<ApplyPermissionBean>>() {
                    @Override
                    public void onNext(DxResponse<ApplyPermissionBean> permissionBeanDxResponse) {
                        CProgressDialogUtils.cancelProgressDialog();
                        if (permissionBeanDxResponse == null) {
                            ToastUtil.showToast(MainActivity.this, "未知异常,请联系管理员");
                            return;
                        }
                        if (permissionBeanDxResponse.getCode() != 0 || permissionBeanDxResponse.getData() == null) {
                            ToastUtil.showToast(MainActivity.this, permissionBeanDxResponse.getMessage() + "");
                            return;
                        }
                        if (!permissionBeanDxResponse.getData().isSuccess()) {
                            ToastUtil.showToast(MainActivity.this, "授权失败");
                            return;
                        }
                        play(moviesBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        CProgressDialogUtils.cancelProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        CProgressDialogUtils.cancelProgressDialog();
                    }
                }
        );

    }

    private void play(MoviesBean moviesBean) {
        if (!TextUtil.isValidate(moviesBean.getPlayPath())) {
            moviesBean.setPlayPath(VideoFormat.encryptToCache(MainActivity.this, moviesBean.getFilePath()));
        }
        if (!isHeadSetOn()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("提示");
            builder.setMessage("请先插入耳机");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();
            return;
        }
        if (TextUtil.isValidate(moviesBean.getPlayPath())) {
            Intent intent = new Intent(MainActivity.this, ViedoActivity.class);
            intent.putExtra("data", moviesBean);
            startActivity(intent);
        }
    }

    private boolean isHeadSetOn() {
        AudioManager localAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        boolean isHeadSetOn = localAudioManager.isWiredHeadsetOn();
        return isHeadSetOn;
    }

}
