package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_update_version)
    RelativeLayout rlUpdateVersion;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.rl_language)
    RelativeLayout rlLanguage;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;
    private Intent intent;

    @Override
    protected int setthislayout() {
        return R.layout.setting_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        rlExit.setOnClickListener(this);
        rlUpdateVersion.setOnClickListener(this);
        rlLanguage.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                intent = new Intent(context, MainActivity.class);
                intent.putExtra("thispage", 4);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_exit:
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                break;
            case R.id.rl_language:
                startActivity(new Intent(context, LanguageActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra("thispage", 4);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
