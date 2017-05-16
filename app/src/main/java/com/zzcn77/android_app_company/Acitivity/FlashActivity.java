package com.zzcn77.android_app_company.Acitivity;


import android.content.Intent;
import com.umeng.analytics.MobclickAgent;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;


/**
 * Created by 赵磊 on 2017/5/12.
 */

public class FlashActivity extends BaseActivity {
    @Override
    protected int setthislayout() {
        return R.layout.flash_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    protected void initData() {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("启动画面"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("启动画面"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }
}
