package com.zzcn77.android_app_company.Acitivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/18.
 */

public class ForGetActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_get)
    Button btnGet;
    public static final int time = 10;

    @Override
    protected int setthislayout() {
        return R.layout.forget_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {
        btnGet.setOnClickListener(this);
        btnOk.setOnClickListener(this);

    }

    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                getcaptcha();
                break;
            case R.id.btn_ok:

                toreset();


                break;
        }
    }

    private void toreset() {

        if (etEmail.getText().toString().trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.emailisEmpty));
            return;
        }

        if (!etEmail.getText().toString().trim().matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$")) {
            EasyToast.showShort(context, getResources().getString(R.string.emailisnotregx));
            return;
        }

        if (etCaptcha.getText().toString().trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.CaptchaisEmpty));
            return;
        }

        if (etPassword.getText().toString().trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.password));
            return;
        }

        if (etPasswordAgain.getText().toString().trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.passwordagain));
            return;
        }

        if (!etPassword.getText().toString().trim().equals(etPasswordAgain.getText().toString().trim())) {
            EasyToast.showShort(context, getResources().getString(R.string.passwordisinconformity));
            return;
        }
        // TODO: 2017/5/18  重置密码
        finish();
    }

    private void getcaptcha() {

        if (etEmail.getText().toString().trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.emailisEmpty));
            return;
        }

        if (!etEmail.getText().toString().trim().matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$")) {
            EasyToast.showShort(context, getResources().getString(R.string.emailisnotregx));
            return;
        }


        //// TODO: 2017/5/18  发送验证码

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = time; i > 0; i--) {
                    try {
                        Thread.sleep(1000);
                        final int finalI = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btnGet.setEnabled(false);
                                btnGet.setText(String.valueOf(finalI));
                                if (btnGet.getText().toString().equals("1")) {
                                    btnGet.setEnabled(true);
                                    btnGet.setText(getResources().getText(R.string.get));
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        btnGet.setEnabled(true);
                        EasyToast.showShort(context, getResources().getString(R.string.iserror));
                    }

                }
            }
        }.start();
    }
}
