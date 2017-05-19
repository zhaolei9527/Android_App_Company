package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_btn_skip)
    Button btnBtnSkip;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forgetPassword)
    TextView tvForgetPassword;
    private String accountor;
    private String password;

    @Override
    protected int setthislayout() {
        return R.layout.login_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
        btnBtnSkip.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgetPassword.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    private void getetcontent() {

        accountor = etAccount.getText().toString();
        password = etPassword.getText().toString();
        if (accountor.trim().isEmpty() || password.trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.AccountorPasswordisEmpty));
        } else {
            // TODO: 2017/5/18
            //登录校验密码，
            gotoMain();
        }
    }

    private void gotoMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                getetcontent();
                break;
            case R.id.btn_btn_skip:
                gotoMain();
                break;
            case R.id.tv_forgetPassword:
                startActivity(new Intent(context, ForGetActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(context, RegisterActivity.class));
                break;

        }
    }

}
