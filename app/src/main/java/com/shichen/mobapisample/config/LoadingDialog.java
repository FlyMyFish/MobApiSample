package com.shichen.mobapisample.config;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shichen.mobapisample.R;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class LoadingDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imgLoading = (ImageView) view.findViewById(R.id.img_loading);
        imgLoading.setImageDrawable(new MoonDrawable(getContext(), imgLoading));
        TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        return view;
    }

    private String msg;

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
