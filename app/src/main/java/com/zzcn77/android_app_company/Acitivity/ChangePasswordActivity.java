package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zzcn77.android_app_company.Adapter.EditpwdBean;
import com.zzcn77.android_app_company.Base.BaseActivity;
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
 * Created by 赵磊 on 2017/5/24.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_oldpassword)
    EditText etOldpassword;
    @BindView(R.id.et_newpassword)
    EditText etNewpassword;
    @BindView(R.id.et_newpasswordagain)
    EditText etNewpasswordagain;
    @BindView(R.id.btn_save)
    Button btnSave;
    private Object password;

    @Override
    protected int setthislayout() {
        return R.layout.change_password_layout;
    }

    @Override
    protected void initview() {
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_save:
                String Oldpassword = etOldpassword.getText().toString().trim();
                String Newpassword = etNewpassword.getText().toString().trim();
                String Newpasswordagain = etNewpasswordagain.getText().toString().trim();
                if (Oldpassword.isEmpty()) {
                    Toast.makeText(context, etOldpassword.getHint(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Newpassword.isEmpty()) {
                    Toast.makeText(context, etNewpassword.getHint(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Newpasswordagain.isEmpty()) {
                    Toast.makeText(context, etNewpasswordagain.getHint(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Newpassword.equals(Newpasswordagain)) {
                    Toast.makeText(context, getResources().getString(R.string.passwordisinconformity), Toast.LENGTH_SHORT).show();
                    return;
                }
                final String oldpassword = MD5Utils.md5(Oldpassword);
                Oldpassword = MD5Utils.md5(oldpassword);
                password = SPUtil.get(context, "password", "");
                if (!Oldpassword.equals(password)) {
                    Toast.makeText(context, "原密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }

                String newpasswordagain = MD5Utils.md5(Newpasswordagain);
                newpasswordagain = MD5Utils.md5(newpasswordagain);
                if (newpasswordagain.equals(password)) {
                    Toast.makeText(context, "新密码与原密码相同", Toast.LENGTH_SHORT).show();
                    return;
                }


                final Dialog dialog = Utils.showLoadingDialog(context);

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                final String finalNewpasswordagain = newpasswordagain;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "editpwd", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String decode = Utils.decode(s);
                        if (decode.isEmpty()) {
                            EasyToast.showShort(context, "网络异常，请稍后再试");
                        } else {
                            EditpwdBean editpwdBean = new Gson().fromJson(decode, EditpwdBean.class);
                            if (editpwdBean.getStu().equals("1")) {
                                dialog.dismiss();
                                SPUtil.remove(context, "id");
                                SPUtil.remove(context, "account");
                                SPUtil.remove(context, "password");
                                SPUtil.remove(context, "email");
                                Toast.makeText(context, "密码修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, LoginActivity.class));
                            } else {
                                EasyToast.showShort(context, "服务器异常，请稍后再试");
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        EasyToast.showShort(context, "网络异常，请稍后再试");
                    }
                })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("key", UrlUtils.key);
                        map.put("id", String.valueOf(SPUtil.get(context, "id", "")));
                        map.put("oldpwd", etOldpassword.getText().toString());
                        map.put("newpwd", finalNewpasswordagain);
                        return map;
                    }
                };

                boolean connected = Utils.isConnected(context);
                if (connected) {
                    requestQueue.add(stringRequest);
                } else {
                    EasyToast.showShort(context, "网络异常，未连接网络");
                }
                break;
        }

    }
}
