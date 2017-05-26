package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.RegistBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.MD5Utils;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

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
    private String passwordmd5;

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

        if (!phone.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")) {
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

        if (password.length() < 6) {
            EasyToast.showShort(context,"密码长度过短，最少六位密码");
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
        final Dialog dialog = Utils.showLoadingDialog(context);

            passwordmd5 = MD5Utils.md5(passwordAgain);
            passwordmd5 = MD5Utils.md5(passwordmd5);


        Toast.makeText(context, account, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, phone, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "regist", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    dialog.dismiss();
                    EasyToast.showShort(context, "网络异常，请稍后再试");
                } else {
                    dialog.dismiss();
                    RegistBean registBean = new Gson().fromJson(decode, RegistBean.class);
                    if (registBean.getStu().equals("1")) {
                        // TODO: 2017/5/19 注册
                        if (registBean.getMsg().contains("注册成功")) {
                            EasyToast.showShort(context, getResources().getString(R.string.goodregister));
                            finish();
                        } else {

                        }
                    } else {
                        if (registBean.getMsg().contains("该用户名已注册")) {
                            Toast.makeText(context, "用户名已存在", Toast.LENGTH_LONG).show();
                        } else if (registBean.getMsg().contains("该邮箱已注册")) {
                            Toast.makeText(context, "该邮箱已注册", Toast.LENGTH_LONG).show();
                        } else if (registBean.getMsg().contains("该手机号已注册")) {
                            Toast.makeText(context, "该手机号已注册", Toast.LENGTH_LONG).show();
                        } else {
                            EasyToast.showShort(context, "服务器异常，请稍后再试");
                        }
                        dialog.dismiss();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                EasyToast.showShort(context, "网络异常，请稍后再试");
                dialog.dismiss();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("username", account);
                map.put("tel", phone);
                map.put("email", emali);
                map.put("password", passwordmd5);


                return map;
            }
        };

        boolean connected = Utils.isConnected(context);
        if (connected) {
            requestQueue.add(stringRequest);
        } else {
            dialog.dismiss();
            EasyToast.showShort(context, "网络异常，未连接网络");
        }


    }
}
