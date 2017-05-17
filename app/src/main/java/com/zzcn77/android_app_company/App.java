package com.zzcn77.android_app_company;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.zzcn77.android_app_company.Utils.Utils;

import cn.refactor.multistatelayout.MultiStateConfiguration;
import cn.refactor.multistatelayout.MultiStateLayout;


/**
 * Created by 赵磊 on 2017/5/12.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token

                Utils.d(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Utils.d(s);
                Utils.d(s1);
            }
        });

        MultiStateConfiguration.Builder builder = new MultiStateConfiguration.Builder();
        builder.setCommonEmptyLayout(R.layout.layout_empty)
                .setCommonErrorLayout(R.layout.layout_error)
                .setCommonLoadingLayout(R.layout.layout_loading);
        MultiStateLayout.setConfiguration(builder);
        Fresco.initialize(this);
    }
}
