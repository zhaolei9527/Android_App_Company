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
    //
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
//    private String phone;
//    private String emali;
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
//        phone = etPhone.getText().toString().trim();
//        if (phone.isEmpty()) {
//            EasyToast.showShort(context, getResources().getString(R.string.phone));
//            return;
//        }
//        emali = etEmail.getText().toString().trim();
//        if (emali.isEmpty()) {
//            EasyToast.showShort(context, getResources().getString(R.string.email));
//            return;
//        }
//        if (!emali.matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$")) {
//            EasyToast.showShort(context, getResources().getString(R.string.emailisnotregx));
//            return;
//        }
        password = etPassword.getText().toString().trim();
        if (password.isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.password));
            return;
        }
        if (password.length() < 6) {
            EasyToast.showShort(context, getString(R.string.passwordistolow));
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "regist", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    if (dialog != null)
                        dialog.dismiss();
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    if (dialog != null)
                        dialog.dismiss();
                    RegistBean registBean = new Gson().fromJson(decode, RegistBean.class);
                    if (registBean.getStu().equals("1")) {
                        if (registBean.getMsg().contains("注册成功")) {
                            if (context != null) {
                                EasyToast.showShort(context, getResources().getString(R.string.goodregister));
                                finish();
                            }
                        } else {
                        }
                    } else {
                        if (registBean.getMsg().contains("该用户名已注册")) {
                            if (context != null)
                                Toast.makeText(context, R.string.Theusernamealreadyexists, Toast.LENGTH_LONG).show();
                        } else if (registBean.getMsg().contains("该邮箱已注册")) {
                            if (context != null)
                                Toast.makeText(context, R.string.Themailboxhasbeenregistered, Toast.LENGTH_LONG).show();
                        } else if (registBean.getMsg().contains("该手机号已注册")) {
                            if (context != null)
                                Toast.makeText(context, R.string.Thephonenumberhasbeenregistered, Toast.LENGTH_LONG).show();
                        } else {
                            if (context != null)
                                EasyToast.showShort(context, getString(R.string.Abnormalserver));
                        }
                        if (dialog != null)
                            dialog.dismiss();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                if (dialog != null)
                    dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("username", account);
                map.put("tel", "");
                map.put("email", "");
                map.put("password", passwordmd5);
                return map;
            }
        };
        boolean connected = Utils.isConnected(context);
        if (connected) {
            requestQueue.add(stringRequest);
        } else {
            if (dialog != null)
                dialog.dismiss();
            if (context != null)
                EasyToast.showShort(context, getString(R.string.Notconnect));
        }
    }
}
