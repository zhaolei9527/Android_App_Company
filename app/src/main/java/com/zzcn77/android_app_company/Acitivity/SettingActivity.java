package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.SPUtil;

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

        try {
            String versionName = getVersionName();
            tvVersion.setText(versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
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
                SPUtil.remove(context, "id");
                SPUtil.remove(context, "account");
                SPUtil.remove(context, "password");
                SPUtil.remove(context, "email");
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
