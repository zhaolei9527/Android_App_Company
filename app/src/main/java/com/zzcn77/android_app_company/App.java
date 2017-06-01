package com.zzcn77.android_app_company;

import android.app.Application;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.zzcn77.android_app_company.Service.AdvanceLoadX5Service;
import com.zzcn77.android_app_company.Utils.CrashHandler;
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
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDisplayNotificationNumber(0);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//呼吸灯
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//振动
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
        preinitX5WebCore();
        //预加载x5内核
        Intent intent = new Intent(this, AdvanceLoadX5Service.class);
        startService(intent);
    }
    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口
        }
    }


}
