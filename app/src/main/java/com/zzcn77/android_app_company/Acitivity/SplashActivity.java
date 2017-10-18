package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
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
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zzcn77.android_app_company.Base.BaseActivity;
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
    //
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

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, TheEntranceActivity.class));
                finish();
            }
        }, 2000);


    }

    @Override
    protected void initData() {
        String account = (String) SPUtil.get(context, "account", "");
        String password = (String) SPUtil.get(context, "password", "");
        if (account.trim().isEmpty() || password.trim().isEmpty()) {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        } else {
            if (context != null) {
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
                            final LoginBean loginBean = new Gson().fromJson(decode, LoginBean.class);
                            if (loginBean.getStu().equals("1")) {
                                // TODO: 2017/5/19 注册
                                if (loginBean.getMsg().contains("登陆成功")) {
                                    Toast.makeText(context, R.string.welcomeback, Toast.LENGTH_SHORT).show();
                                    SPUtil.putAndApply(context, "account", loginBean.getRes().getUsername());
                                    SPUtil.putAndApply(context, "password", loginBean.getRes().getPassword());
                                    SPUtil.putAndApply(context, "id", loginBean.getRes().getId());
                                    SPUtil.putAndApply(context, "email", loginBean.getRes().getEmail());
                                    if (!loginBean.getRes().getId().isEmpty()) {
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                super.run();
                                                //注册失败会抛出HyphenateException
                                                try {
                                                    if (!String.valueOf(SPUtil.get(context, "id", "")).isEmpty()) {
                                                        EMClient.getInstance().createAccount(String.valueOf(SPUtil.get(context, "id", "")), String.valueOf(SPUtil.get(context, "id", "")));//同步方法
                                                    }
                                                } catch (HyphenateException e) {
                                                    e.printStackTrace();
                                                }
                                                if (!String.valueOf(SPUtil.get(context, "id", "")).isEmpty()) {
                                                    EMClient.getInstance().login(String.valueOf(SPUtil.get(context, "id", "")), String.valueOf(SPUtil.get(context, "id", "")), new EMCallBack() {//回调
                                                        @Override
                                                        public void onSuccess() {
                                                            EMClient.getInstance().groupManager().loadAllGroups();
                                                            EMClient.getInstance().chatManager().loadAllConversations();
                                                            EMClient.getInstance().updateCurrentUserNick(String.valueOf(SPUtil.get(context, "account", "")));
                                                            Log.d("main", "登录聊天服务器成功！");
                                                            if (loginBean.getRes().getType().equals("2")) {
                                                                startActivity(new Intent(context, ChatListActivity.class));
                                                                finish();
                                                                return;
                                                            }
                                                            gotoMain();
                                                        }

                                                        @Override
                                                        public void onProgress(int progress, String status) {
                                                        }

                                                        @Override
                                                        public void onError(int code, String message) {
                                                            Log.d("main", "登录聊天服务器失败！");
                                                        }

                                                    });
                                                }
                                            }
                                        }.start();
                                    }
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
                                } else if (loginBean.getMsg().contains("会员信息不存在")) {
                                    Toast.makeText(context, getString(R.string.Usernamedoesnotexist), Toast.LENGTH_LONG).show();
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
}
