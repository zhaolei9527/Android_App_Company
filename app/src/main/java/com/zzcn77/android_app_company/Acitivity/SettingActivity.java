package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.VersionBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.UpDateDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/22.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_update_version)
    RelativeLayout rlUpdateVersion;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.rl_language)
    RelativeLayout rlLanguage;
    @BindView(R.id.rl_exit)
    RelativeLayout rlExit;

    @Override
    protected int setthislayout() {
        return R.layout.setting_layout;
    }

    @Override
    protected void initview() {
        try {
            String versionName = getVersionName();
            tvVersion.setText(versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    private int getversionCode() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        int versionCode = packInfo.versionCode;
        return versionCode;
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        rlExit.setOnClickListener(this);
        rlUpdateVersion.setOnClickListener(this);
        rlLanguage.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String language = (String) SPUtil.get(context, "Lanuage", "");
        if (language.isEmpty()) {
            language = getResources().getString(R.string.auto);
        }
        if (language.equals("cn")) {
            language = getResources().getString(R.string.cn);
        }
        if (language.equals("en")) {
            language = getResources().getString(R.string.en);
        }
        tvLanguage.setText(language);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_exit:
                SPUtil.remove(context, "id");
                SPUtil.remove(context, "account");
                SPUtil.remove(context, "password");
                SPUtil.remove(context, "email");
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                break;
            case R.id.rl_language:
                startActivity(new Intent(context, LanguageActivity.class));
                break;
            case R.id.rl_update_version:
                final Dialog dialog = Utils.showLoadingDialog(context);
                dialog.show();

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "version", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String decode = Utils.decode(s);
                        if (decode.isEmpty()) {
                            EasyToast.showShort(context, "网络异常，请稍后再试");
                            dialog.dismiss();
                        } else {
                            VersionBean versionBean = new Gson().fromJson(decode, VersionBean.class);
                            if (String.valueOf(versionBean.getStu()).equals("1")) {
                                try {
                                    int versionCode = getversionCode();
                                    int Android_bnum= Integer.parseInt(versionBean.getRes().getAndroid_bnum());
                                    if (versionCode<Android_bnum){
                                        dialog.dismiss();
                                        UpDateDialog upDateDialog = new UpDateDialog();
                                        upDateDialog.UpDateDialog(context, "重要更新", versionBean.getRes().getAndroid_content(),versionBean.getRes().getAndroid());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    dialog.dismiss();

                                }
                            } else {
                                dialog.dismiss();

                                EasyToast.showShort(context, "服务器异常，请稍后再试");
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
                    EasyToast.showShort(context, "网络异常，未连接网络");
                    dialog.dismiss();

                }

                break;
        }
    }

}
