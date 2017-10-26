package com.yulian.platform.Acitivity;

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
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.EmailCodeBean;
import com.yulian.platform.Bean.FindpwdBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.MD5Utils;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    Timer timer;
    private TimerTask task;
    public static int time = 60;
    private Thread thread;
    private String md5password;
    private Runnable sendable;

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

        if (etPassword.getText().toString().length() < 6) {
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
                    if (context != null) {
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                    }
                } else {
                    FindpwdBean findpwdBean = new Gson().fromJson(decode, FindpwdBean.class);
                    if (String.valueOf(findpwdBean.getStu()).equals("1")) {
                        if (findpwdBean.getMsg().contains("密码找回成功")) {
                            if (context != null) {
                                Toast.makeText(context, R.string.PASSWORDFORGET, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    } else {
                        if (findpwdBean.getMsg().contains("该邮箱验证码已失效")) {
                            if (context != null) {
                                Toast.makeText(context, R.string.Thecaptchahasfailed, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (context != null) {
                                Toast.makeText(context, findpwdBean.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (context != null) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                }
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
            if (context != null) {
                EasyToast.showShort(context, getString(R.string.Notconnect));
            }
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
        time = 60;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      // UI thread
                    @Override
                    public void run() {
                        time--;
                        btnGet.setText("" + time);
                        if (time < 0) {
                            timer.cancel();
                            btnGet.setText(getString(R.string.get));
                            btnGet.setEnabled(true);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
        //// TODO: 2017/5/18  发送验证码
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "emailcode", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    if (context != null) {
                        timer.cancel();
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                    }
                    if (btnGet != null) {
                        btnGet.setEnabled(true);
                        btnGet.setText(getResources().getText(R.string.get));
                    }
                } else {
                    EmailCodeBean emailCodeBean = new Gson().fromJson(decode, EmailCodeBean.class);
                    if (String.valueOf(emailCodeBean.getStu()).equals("1")) {
                        if (emailCodeBean.getMsg().contains("发送成功")) {
                            if (context != null) {
                                Toast.makeText(context, R.string.sendsuccessfully, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (timer != null)
                            timer.cancel();
                        if (btnGet != null) {
                            btnGet.setEnabled(true);
                            btnGet.setText(getResources().getText(R.string.get));
                        }
                        if (emailCodeBean.getMsg().toString().contains("该邮箱还没有注册")) {
                            if (context != null) {
                                Toast.makeText(context, R.string.mailboxnotregistered, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (context != null) {
                                Toast.makeText(context, emailCodeBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (context != null) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                }
                if (btnGet != null) {
                    timer.cancel();
                    btnGet.setEnabled(true);
                    btnGet.setText(getResources().getText(R.string.get));
                }
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
            if (context != null) {
                EasyToast.showShort(context, getString(R.string.Notconnect));
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
