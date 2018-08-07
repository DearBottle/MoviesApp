package com.bottle.moviesapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bottle.moviesapp.R;
import com.bottle.moviesapp.utils.TextUtil;


/**
 * Created by admin on 2018/4/26.
 */

public class DxLoadingDialog extends Dialog {
    private TextView mHintView;

    public DxLoadingDialog(@NonNull Context context) {
        super(context,R.style.DxTransparentDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dx_dialog_loading, null);
        mHintView = view.findViewById(R.id.tv_hint);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    @Override
    public void show() {
        show(null);
    }

    public void show(String msg) {
        super.show();
        if (TextUtil.isValidate(msg)) {
            mHintView.setText(msg);
        } else {
            mHintView.setText(R.string.dx_now_loading);
        }
    }

}
