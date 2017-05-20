package com.zzcn77.android_app_company.Base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.badoo.mobile.util.WeakHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.zzcn77.android_app_company.Utils.Utils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 赵磊 on 2017/5/12.
 */

public abstract class BaseActivity extends Activity {
    protected WeakHandler mHandler;
    protected Context context;
    private Unbinder bind;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ready();
        setContentView(setthislayout());
        bind = ButterKnife.bind(this);
        context = this;
        mHandler = new WeakHandler();
        PushAgent.getInstance(context).onAppStart();
        initview();
        initListener();
        initData();
        dialog = Utils.showLoadingDialog(context);
    }


    protected void ready() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();//解除绑定
    }

    protected abstract int setthislayout();

    protected abstract void initview();

    protected abstract void initListener();

    protected abstract void initData();

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}