package com.bottle.moviesapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengbaobao on 2017/3/14.
 */

public class PermissionUtil {

    private final int PERMISSON_REQUESTCODE = 10086;
    private Activity mActivity;
    private AlertDialog dialog;

    public PermissionUtil(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public PermissionUtil(Activity mActivity, RequsetPermissionsListener requsetPermissionsListener) {
        this.mActivity = mActivity;
        this.requsetPermissionsListener = requsetPermissionsListener;
    }


    public void checkPermissions(RequsetPermissionsListener requsetPermissionsListener, String... permissions) {
        this.requsetPermissionsListener = requsetPermissionsListener;
        checkPermissions(permissions);
    }

    /**
     * @since 2.5.0
     */
    public void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(mActivity, needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    public void allOpen(String[] permissions) {
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    showMissingPermissionDialog();
                    return;
                }
            }
        }
        if (requsetPermissionsListener != null) {
            requsetPermissionsListener.onSuccess();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                showMissingPermissionDialog();
            } else {
                if (requsetPermissionsListener != null) {
                    requsetPermissionsListener.onSuccess();
                }
            }
        }
    }

    RequsetPermissionsListener requsetPermissionsListener;

    public interface RequsetPermissionsListener {
        void onSuccess();

    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(mActivity,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示信息
     */
    private void showMissingPermissionDialog() {
        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("提示");
            builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。");
            // 拒绝, 退出应用
            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mActivity.finish();
                        }
                    });
            builder.setPositiveButton("设置",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startAppSettings();
                        }
                    });
            builder.setCancelable(false);
            dialog = builder.create();
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
        mActivity.startActivity(intent);
    }


}
