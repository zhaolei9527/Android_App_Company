package com.zzcn77.android_app_company.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcn77.android_app_company.Acitivity.ChangePasswordActivity;
import com.zzcn77.android_app_company.Acitivity.ConsultActivity;
import com.zzcn77.android_app_company.Acitivity.MyCollectActivity;
import com.zzcn77.android_app_company.Acitivity.SettingActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.SPUtil;

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
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_email)
    TextView tvEmail;

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
        String account = (String) SPUtil.get(mActivity, "account", "");
        String email = (String) SPUtil.get(mActivity, "email", "");
        if (account.trim().isEmpty()) {
            tvAccount.setText("暂未登录");
        } else {
            tvAccount.setText(account);
        }
        tvEmail.setText(email);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_change_password:
                startActivity(new Intent(mActivity, ChangePasswordActivity.class));
                break;
            case R.id.rl_collect:
                startActivity(new Intent(mActivity, MyCollectActivity.class));
                break;
            case R.id.rl_consult:
                startActivity(new Intent(mActivity, ConsultActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                mActivity.finish();
                break;
        }
    }

}
