package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int setthislayout() {
        return R.layout.flash_layout;
    }

    @Override
    protected void ready() {
        super.ready();
       /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String account = (String) SPUtil.get(context, "account", "");
                String password = (String) SPUtil.get(context, "password", "");
                if (account.trim().isEmpty() || password.trim().isEmpty()) {
                    startActivity(new Intent(context, LoginActivity.class));
                } else {
                    startActivity(new Intent(context, MainActivity.class));
                    EasyToast.showLong(context, "欢迎回来");
                }
                finish();
            }
        }, 1000);
    }

    @Override
    protected void initData() {

    }
}
