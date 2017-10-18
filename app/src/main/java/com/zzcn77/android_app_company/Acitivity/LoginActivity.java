package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
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
import com.zzcn77.android_app_company.Bean.LoginBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.MD5Utils;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/18.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    //
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
    private Dialog dialog;

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

    String passwordmd5;

    private void getetcontent() {
        accountor = etAccount.getText().toString();
        password = etPassword.getText().toString();
        if (accountor.trim().isEmpty() || password.trim().isEmpty()) {
            if (context != null) {
                EasyToast.showShort(context, getResources().getString(R.string.AccountorPasswordisEmpty));
            }
        } else {
            if (password.length() < 6) {
                EasyToast.showShort(context, getResources().getString(R.string.passwordistolow));
                return;
            }
            //登录校验密码，
            passwordmd5 = MD5Utils.md5(password);
            passwordmd5 = MD5Utils.md5(passwordmd5);
            final Dialog dialog = Utils.showLoadingDialog(context);
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "login", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    Log.d("LoginActivity", decode);
                    if (decode.isEmpty()) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (context != null) {
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                        }
                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                        if (loginBean.getStu().equals("1")) {
                            // TODO: 2017/5/19 注册
                            if (loginBean.getMsg().contains("登陆成功")) {
                                if (context != null) {
                                    EasyToast.showShort(context, getString(R.string.loginsuccessfully));
                                    SPUtil.putAndApply(context, "account", loginBean.getRes().getUsername());
                                    SPUtil.putAndApply(context, "password", loginBean.getRes().getPassword());
                                    SPUtil.putAndApply(context, "id", loginBean.getRes().getId());
                                    SPUtil.putAndApply(context, "email", loginBean.getRes().getEmail());
                                    if (loginBean.getRes().getType().equals("2")) {
                                        startActivity(new Intent(context, ChatListActivity.class));
                                        finish();
                                        return;
                                    }
                                    gotoMain();
                                }
                            } else {

                            }
                        } else {
                            if (loginBean.getMsg().contains("您已被封号")) {
                                if (context != null)
                                    Toast.makeText(context, getString(R.string.akick), Toast.LENGTH_LONG).show();
                            } else if (loginBean.getMsg().contains("密码有误")) {
                                if (context != null)
                                    Toast.makeText(context, getString(R.string.usernameorpassworderror), Toast.LENGTH_LONG).show();
                            } else if (loginBean.getMsg().contains("用户名不存在")) {
                                if (context != null)
                                    Toast.makeText(context, getString(R.string.Usernamedoesnotexist), Toast.LENGTH_LONG).show();
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
                    map.put("username", accountor);
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

    private void gotoMain() {
        startActivity(new Intent(context, TheEntranceActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                getetcontent();
                break;
            case R.id.btn_btn_skip:
                SPUtil.remove(context, "id");
                SPUtil.remove(context, "account");
                SPUtil.remove(context, "password");
                SPUtil.remove(context, "email");
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
