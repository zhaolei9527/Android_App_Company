package com.zzcn77.android_app_company.Base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.badoo.mobile.util.WeakHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/12.
 */

public abstract class BaseActivity extends Activity {
    protected WeakHandler mHandler;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setthislayout());
        context = this;
        ButterKnife.bind(this);
        mHandler = new WeakHandler();
        PushAgent.getInstance(context).onAppStart();
        initview();
        initListener();
        initData();
    }

    protected abstract int setthislayout();

    protected abstract void initview();

    protected abstract void initListener();

    protected abstract void initData();

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}