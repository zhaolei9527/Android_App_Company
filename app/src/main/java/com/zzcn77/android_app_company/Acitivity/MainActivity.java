package com.zzcn77.android_app_company.Acitivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;

import butterknife.BindView;
import cn.refactor.multistatelayout.MultiStateLayout;
import cn.refactor.multistatelayout.OnStateViewCreatedListener;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.multi_state_layout)
    MultiStateLayout mMultiStateLayout;
    @BindView(R.id.tv)
    TextView tv;

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("主界面"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("主界面"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        tv.setOnClickListener(this);
        mMultiStateLayout.setOnStateViewCreatedListener(new OnStateViewCreatedListener() {
            @Override
            public void onViewCreated(View view, int state) {
                switch (state) {
                    case MultiStateLayout.State.NETWORK_ERROR:
                        view.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mMultiStateLayout.setState(MultiStateLayout.State.LOADING);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mMultiStateLayout.setState(MultiStateLayout.State.CONTENT);
                                    }
                                }, 2000);
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    /**
     * 通过监听keyUp
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                mMultiStateLayout.setState(MultiStateLayout.State.NETWORK_ERROR);


                break;
        }
    }
}
