package com.zzcn77.android_app_company.Base;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badoo.mobile.util.WeakHandler;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.zzcn77.android_app_company.Utils.SPUtil;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 赵磊 on 2017/5/12.
 */
/**
 *
 * ━━━━━━神兽保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━代码无BUG━━━━━━
 */
public abstract class BaseActivity extends Activity {
    //
    protected WeakHandler mHandler;
    protected Context context;
    private Unbinder bind;
    private String lanuage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ready();
        lanuage = (String) SPUtil.get(this, "Lanuage", "");
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (lanuage.isEmpty()) {
            config.locale = Locale.getDefault();
        } else {
            config.locale = lanuage.equals("cn") ? Locale.CHINESE : Locale.ENGLISH;
        }
        // 应用用户选择语言
        resources.updateConfiguration(config, dm);
        setContentView(setthislayout());
        bind = ButterKnife.bind(this);
        context = this;
        mHandler = new WeakHandler();
        PushAgent.getInstance(context).onAppStart();
        initview();
        initListener();
        initData();
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
        String nowlanuage = (String) SPUtil.get(this, "Lanuage", "");
        if (!lanuage.equals(nowlanuage)) {
            lanuage = nowlanuage;
            recreate();
        }

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}