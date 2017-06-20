package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
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
import com.zzcn77.android_app_company.Bean.IndexBean;
import com.zzcn77.android_app_company.Bean.LoginBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected int setthislayout() {
        return R.layout.flash_layout;
    }

    @Override
    protected void ready() {
        super.ready();
       /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {

    }


    private void gotoMain() {
        startActivity(new Intent(context, MainActivity.class));
        finish();
    }

    @Override
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "index", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                } else {
                    IndexBean indexBean = new Gson().fromJson(decode, IndexBean.class);
                    if (indexBean.getStu().equals("1")) {
                        if (context != null) {
                            SPUtil.putAndApply(context, "index", s);
                            String account = (String) SPUtil.get(context, "account", "");
                            String password = (String) SPUtil.get(context, "password", "");
                            if (account.trim().isEmpty() || password.trim().isEmpty()) {
                                startActivity(new Intent(context, LoginActivity.class));
                                finish();
                            } else {
                                if (context!=null){
                                    final Dialog dialog = Utils.showLoadingDialog(context);
                                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "login", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            String decode = Utils.decode(s);
                                            if (decode.isEmpty()) {
                                                dialog.dismiss();
                                                EasyToast.showShort(context, getString(R.string.Networkexception));
                                            } else {
                                                dialog.dismiss();
                                                LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                                                if (loginBean.getStu().equals("1")) {
                                                    // TODO: 2017/5/19 注册
                                                    if (loginBean.getMsg().contains("登陆成功")) {
                                                        Toast.makeText(context, R.string.welcomeback, Toast.LENGTH_SHORT).show();
                                                        SPUtil.putAndApply(context, "account", loginBean.getRes().getUsername());
                                                        SPUtil.putAndApply(context, "password", loginBean.getRes().getPassword());
                                                        SPUtil.putAndApply(context, "id", loginBean.getRes().getId());
                                                        SPUtil.putAndApply(context, "email", loginBean.getRes().getEmail());
                                                        gotoMain();
                                                    } else {

                                                    }
                                                } else {
                                                    if (loginBean.getMsg().contains("您已被封号")) {
                                                        Toast.makeText(context, getString(R.string.akick), Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(context, LoginActivity.class));
                                                        finish();
                                                    } else if (loginBean.getMsg().contains("密码有误")) {
                                                        Toast.makeText(context, getString(R.string.usernameorpassworderror), Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(context, LoginActivity.class));
                                                        finish();
                                                    } else if (loginBean.getMsg().contains("用户名不存在")) {
                                                        Toast.makeText(context, getString(R.string.Usernamedoesnotexist), Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(context, LoginActivity.class));
                                                        finish();
                                                    } else {
                                                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                                                        gotoMain();
                                                    }
                                                    dialog.dismiss();
                                                }
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            volleyError.printStackTrace();
                                            EasyToast.showShort(context, getString(R.string.Networkexception));
                                            dialog.dismiss();
                                            gotoMain();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> map = new HashMap<String, String>();
                                            map.put("key", UrlUtils.key);
                                            map.put("username", String.valueOf(SPUtil.get(context, "account", "")));
                                            map.put("password", String.valueOf(SPUtil.get(context, "password", "")));
                                            return map;
                                        }
                                    };

                                    boolean connected = Utils.isConnected(context);
                                    if (connected) {
                                        requestQueue.add(stringRequest);
                                    } else {
                                        dialog.dismiss();
                                        EasyToast.showShort(context, getString(R.string.Notconnect));
                                    }
                                }
                            }
                        }
                    } else {
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                return map;
            }
        };

        boolean connected = Utils.isConnected(context);
        if (connected) {
            requestQueue.add(stringRequest);
        } else {
            if (context != null) {
                EasyToast.showShort(context, getString(R.string.Notconnect));
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }
    }
}
