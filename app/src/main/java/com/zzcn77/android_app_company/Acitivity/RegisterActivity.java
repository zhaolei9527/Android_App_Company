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
 * Created by 赵磊 on 2017/5/19.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private String account;
    private String phone;
    private String emali;
    private String password;
    private String passwordAgain;

    @Override
    protected int setthislayout() {
        return R.layout.register_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {

        tvRegister.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                finish();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {

        account = etAccount.getText().toString().trim();

        if (account.isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.account));
            return;
        }
        phone = etPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.phone));
            return;
        }

        if (!phone.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")){
            EasyToast.showShort(context, getResources().getString(R.string.phoneisnotregx));
            return;
        }

        emali = etEmail.getText().toString().trim();
        if (emali.isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.email));
            return;
        }

        if (!emali.matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$")) {
            EasyToast.showShort(context, getResources().getString(R.string.emailisnotregx));
            return;
        }

        password = etPassword.getText().toString().trim();
        if (password.isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.password));
            return;
        }

        passwordAgain = etPasswordAgain.getText().toString().trim();
        if (passwordAgain.isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.passwordagain));

            return;
        }

        if (!password.equals(passwordAgain)) {
            EasyToast.showShort(context, getResources().getString(R.string.passwordisinconformity));
            return;
        }

        // TODO: 2017/5/19 注册
        EasyToast.showShort(context, getResources().getString(R.string.goodregister));
        startActivity(new Intent(context, LoginActivity.class));


    }
}
