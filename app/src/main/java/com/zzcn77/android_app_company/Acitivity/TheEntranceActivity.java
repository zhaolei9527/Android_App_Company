package com.zzcn77.android_app_company.Acitivity;

import android.view.Window;
import android.view.WindowManager;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;

public class TheEntranceActivity extends BaseActivity {

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
    protected int setthislayout() {
        return R.layout.activity_the_entrance;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}