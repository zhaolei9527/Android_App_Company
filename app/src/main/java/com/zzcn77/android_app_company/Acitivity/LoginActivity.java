package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
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

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

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

        final Dialog dialog = Utils.showLoadingDialog(context);

        accountor = etAccount.getText().toString();
        password = etPassword.getText().toString();
        if (accountor.trim().isEmpty() || password.trim().isEmpty()) {
            EasyToast.showShort(context, getResources().getString(R.string.AccountorPasswordisEmpty));
        } else {
            // TODO: 2017/5/18
            //登录校验密码，
            try {
                passwordmd5 = MD5Utils.getMD5(MD5Utils.getMD5(password).toLowerCase().toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                dialog.dismiss();
                EasyToast.showShort(context, "网络异常，请稍后再试");
                return;
            }


            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "login", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        dialog.dismiss();
                        EasyToast.showShort(context, "网络异常，请稍后再试");
                    } else {
                        dialog.dismiss();
                        LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                        if (loginBean.getStu().equals("1")) {
                            // TODO: 2017/5/19 注册
                            if (loginBean.getMsg().contains("登陆成功")) {
                                EasyToast.showShort(context, "登录成功");
                                SPUtil.putAndApply(context, "account", loginBean.getRes().getUsername());
                                SPUtil.putAndApply(context, "password", loginBean.getRes().getPassword());
                                SPUtil.putAndApply(context, "id", loginBean.getRes().getId());
                                SPUtil.putAndApply(context, "email", loginBean.getRes().getEmail());
                                gotoMain();

                            } else {

                            }
                        } else {
                            if (loginBean.getMsg().contains("您已被封号")) {
                                Toast.makeText(context, "您已被封号", Toast.LENGTH_LONG).show();
                            } else if (loginBean.getMsg().contains("密码有误")) {
                                Toast.makeText(context, "帐号或密码有误", Toast.LENGTH_LONG).show();
                            }else if (loginBean.getMsg().contains("用户名不存在")) {
                                Toast.makeText(context, "用户名不存在", Toast.LENGTH_LONG).show();
                            }
                            else{
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
                    map.put("username", accountor);
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
