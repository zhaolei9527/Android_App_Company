package com.zzcn77.android_app_company.Acitivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.zzcn77.android_app_company.Bean.EmailCodeBean;
import com.zzcn77.android_app_company.Bean.FindpwdBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.MD5Utils;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

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
    public static final int time = 30;
    private Thread thread;
    private String md5password;

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

        thread = new Thread() {
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
                                if (btnGet != null) {
                                    btnGet.setEnabled(false);
                                    btnGet.setText(String.valueOf(finalI));
                                    if (btnGet.getText().toString().equals("1")) {
                                        btnGet.setEnabled(true);
                                        btnGet.setText(getResources().getText(R.string.get));
                                    }
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
        };
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
            md5password = MD5Utils.md5(etPassword.getText().toString());
            md5password = MD5Utils.md5(md5password);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "findpwd", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, "网络异常，请稍后再试");
                } else {
                    FindpwdBean findpwdBean = new Gson().fromJson(decode, FindpwdBean.class);
                    if (String.valueOf(findpwdBean.getStu()).equals("1")) {
                        if (findpwdBean.getMsg().contains("密码找回成功")) {
                            Toast.makeText(context, "密码找回成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(context, findpwdBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
                        EasyToast.showShort(context, "服务器异常，请稍后再试");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                EasyToast.showShort(context, "网络异常，请稍后再试");
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("email", etEmail.getText().toString().trim());
                map.put("ecode", etCaptcha.getText().toString().trim());
                map.put("password", md5password);
                return map;
            }
        };

        boolean connected = Utils.isConnected(context);
        if (connected) {
            requestQueue.add(stringRequest);
        } else {
            EasyToast.showShort(context, "网络异常，未连接网络");
        }

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "emailcode", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, "网络异常，请稍后再试");
                } else {
                    EmailCodeBean emailCodeBean = new Gson().fromJson(decode, EmailCodeBean.class);
                    if (String.valueOf(emailCodeBean.getStu()).equals("1")) {
                        if (emailCodeBean.getMsg().contains("发送成功")) {
                            Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
                            thread.start();
                        }
                    } else {
                        if (emailCodeBean.getMsg().toString().contains("该邮箱还没有注册")) {
                            Toast.makeText(context, "该邮箱还没有注册", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, emailCodeBean.getMsg().toString(), Toast.LENGTH_SHORT).show();

                        }
                        EasyToast.showShort(context, "服务器异常，请稍后再试");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                EasyToast.showShort(context, "网络异常，请稍后再试");
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("email", etEmail.getText().toString().trim());
                return map;
            }
        };

        boolean connected = Utils.isConnected(context);
        if (connected) {
            requestQueue.add(stringRequest);
        } else {
            EasyToast.showShort(context, "网络异常，未连接网络");
        }


    }
}
