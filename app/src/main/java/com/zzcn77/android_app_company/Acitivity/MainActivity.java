package com.zzcn77.android_app_company.Acitivity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.VersionBean;
import com.zzcn77.android_app_company.Fragment.DemoFragment;
import com.zzcn77.android_app_company.Fragment.HomeFragment;
import com.zzcn77.android_app_company.Fragment.MeFragment;
import com.zzcn77.android_app_company.Fragment.ProductFragment;
import com.zzcn77.android_app_company.Fragment.SchemeFragment;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.Other;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.UpDateDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.fl_main)
    FrameLayout flMain;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.img_product)
    ImageView imgProduct;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.img_scheme)
    ImageView imgScheme;
    @BindView(R.id.tv_scheme)
    TextView tvScheme;
    @BindView(R.id.ll_scheme)
    LinearLayout llScheme;
    @BindView(R.id.img_demo)
    ImageView imgDemo;
    @BindView(R.id.tv_demo)
    TextView tvDemo;
    @BindView(R.id.ll_demo)
    LinearLayout llDemo;
    @BindView(R.id.img_me)
    ImageView imgMe;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.ll_me)
    LinearLayout llMe;
    private String thispage = Other.HOME;
    private TextView[] views;

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();

    }

    @Override
    protected void ready() {
        super.ready();
    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initview() {
        views = new TextView[]{tvHome, tvDemo, tvMe, tvProduct, tvScheme};
    }


    @Override
    protected void initListener() {
        llHome.setOnClickListener(this);
        llDemo.setOnClickListener(this);
        llMe.setOnClickListener(this);
        llProduct.setOnClickListener(this);
        llScheme.setOnClickListener(this);
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
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "version", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    VersionBean versionBean = new Gson().fromJson(decode, VersionBean.class);
                    if (String.valueOf(versionBean.getStu()).equals("1")) {
                        try {
                            int versionCode = getversionCode();
                            int Android_bnum = Integer.parseInt(versionBean.getRes().getAndroid_bnum());
                            if (versionCode < Android_bnum) {
                                UpDateDialog upDateDialog = new UpDateDialog();
                                upDateDialog.UpDateDialog(context, getString(R.string.Importantupdate), versionBean.getRes().getAndroid_content(), versionBean.getRes().getAndroid());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    } else {
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                EasyToast.showShort(context, getString(R.string.Networkexception));
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
            EasyToast.showShort(context, getString(R.string.Notconnect));

        }


        if (!IntentUtil.isBundleEmpty(getIntent())) {
            int thispage = getIntent().getIntExtra("thispage", 0);
            if (thispage == 4) {
                this.thispage = Other.ME;
            }
        }
        changecheck(thispage);
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
                /*以下为自定义提示语、按钮文字
                .setDeniedMessage()
                .setDeniedCloseBtn()
                .setDeniedSettingBtn()
                .setRationalMessage()
                .setRationalBtn()*/
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(context, R.string.Thepermissionapplicationisrejected, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //--------------使用onKeyDown()干掉他--------------

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(MainActivity.this, R.string.Clicktheexitprogramagain, Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_home:
                if (!thispage.equals(Other.HOME)) {
                    thispage = Other.HOME;
                    changecheck(thispage);
                }
                break;
            case R.id.ll_scheme:
                if (!thispage.equals(Other.SCHEME)) {
                    thispage = Other.SCHEME;
                    changecheck(thispage);
                }
                break;
            case R.id.ll_product:
                if (!thispage.equals(Other.PRODUCT)) {
                    thispage = Other.PRODUCT;
                    changecheck(thispage);
                }
                break;
            case R.id.ll_demo:
                if (!thispage.equals(Other.DEMO)) {
                    thispage = Other.DEMO;
                    changecheck(thispage);
                }
                break;
            case R.id.ll_me:
                if (!thispage.equals(Other.ME)) {
                    thispage = Other.ME;
                    changecheck(thispage);
                }
                break;
        }

    }

    private void changecheck(String thispage) {
        if (thispage.equals(Other.HOME)) {
            imgHome.setBackground(getResources().getDrawable(R.mipmap.i_index2));
            imgDemo.setBackground(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackground(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackground(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackground(getResources().getDrawable(R.mipmap.i_fangan1));

            for (int i = 0; i < views.length; i++) {
                if (views[i] != tvHome) {
                    views[i].setTextColor(getResources().getColor(R.color.text_gray));
                } else {
                    views[i].setTextColor(getResources().getColor(R.color.text_blue));
                }
            }

            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fl_main, homeFragment);
            transaction.commit();

        } else if (thispage.equals(Other.ME)) {
            imgHome.setBackground(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackground(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackground(getResources().getDrawable(R.mipmap.i_wode2));
            imgProduct.setBackground(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackground(getResources().getDrawable(R.mipmap.i_fangan1));
            for (int i = 0; i < views.length; i++) {
                if (views[i] != tvMe) {
                    views[i].setTextColor(getResources().getColor(R.color.text_gray));
                } else {
                    views[i].setTextColor(getResources().getColor(R.color.text_blue));
                }
            }
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            MeFragment meFragment = new MeFragment();
            transaction.replace(R.id.fl_main, meFragment);
            transaction.commit();

        } else if (thispage.equals(Other.DEMO)) {
            imgHome.setBackground(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackground(getResources().getDrawable(R.mipmap.i_yanshi2));
            imgMe.setBackground(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackground(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackground(getResources().getDrawable(R.mipmap.i_fangan1));
            for (int i = 0; i < views.length; i++) {
                if (views[i] != tvDemo) {
                    views[i].setTextColor(getResources().getColor(R.color.text_gray));
                } else {
                    views[i].setTextColor(getResources().getColor(R.color.text_blue));
                }
            }
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            DemoFragment demoFragment = new DemoFragment();
            transaction.replace(R.id.fl_main, demoFragment);
            transaction.commit();
        } else if (thispage.equals(Other.PRODUCT)) {
            imgHome.setBackground(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackground(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackground(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackground(getResources().getDrawable(R.mipmap.i_chanpin2));
            imgScheme.setBackground(getResources().getDrawable(R.mipmap.i_fangan1));
            for (int i = 0; i < views.length; i++) {
                if (views[i] != tvProduct) {
                    views[i].setTextColor(getResources().getColor(R.color.text_gray));
                } else {
                    views[i].setTextColor(getResources().getColor(R.color.text_blue));
                }
            }
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            ProductFragment productFragment = new ProductFragment();
            transaction.replace(R.id.fl_main, productFragment);
            transaction.commit();
        } else if (thispage.equals(Other.SCHEME)) {
            imgHome.setBackground(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackground(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackground(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackground(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackground(getResources().getDrawable(R.mipmap.i_fangan2));
            for (int i = 0; i < views.length; i++) {
                if (views[i] != tvScheme) {
                    views[i].setTextColor(getResources().getColor(R.color.text_gray));
                } else {
                    views[i].setTextColor(getResources().getColor(R.color.text_blue));
                }
            }
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            SchemeFragment schemeFragment = new SchemeFragment();
            transaction.replace(R.id.fl_main, schemeFragment);
            transaction.commit();
        }

    }

}
