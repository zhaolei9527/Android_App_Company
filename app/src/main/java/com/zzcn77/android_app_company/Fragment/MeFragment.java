package com.zzcn77.android_app_company.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zzcn77.android_app_company.Acitivity.SettingActivity;
import com.zzcn77.android_app_company.R;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;
    @BindView(R.id.rl_consult)
    RelativeLayout rlConsult;
    @BindView(R.id.rl_change_password)
    RelativeLayout rlChangePassword;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_me_layout;
    }

    @Override
    protected void initView() {
        super.initView();

        rlChangePassword.setOnClickListener(this);
        rlConsult.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        rlCollect.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_change_password:
                Toast.makeText(mActivity, "更改密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_collect:
                Toast.makeText(mActivity, "我的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_consult:
                Toast.makeText(mActivity, "我的咨询", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                mActivity.finish();
                break;
        }
    }
}
