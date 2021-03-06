package com.yulian.platform.Acitivity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
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
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.VersionBean;
import com.yulian.platform.Fragment.DemoFragment;
import com.yulian.platform.Fragment.HomeFragment;
import com.yulian.platform.Fragment.MeFragment;
import com.yulian.platform.Fragment.ProductFragment;
import com.yulian.platform.Fragment.SchemeFragment;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.IntentUtil;
import com.yulian.platform.Utils.Other;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.View.UpDateDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    private boolean connected;

    public void onResume() {
        super.onResume();
        String usertype = (String) SPUtil.get(context, "usertype", "");
        if ("2".equals(usertype)) {
            finish();
        }
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {


        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
            }
        });


        super.onDestroy();
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

        new Thread() {
            @Override
            public void run() {
                super.run();
                if (!String.valueOf(SPUtil.get(context, "id", "")).isEmpty()) {
                    EMClient.getInstance().login(String.valueOf(SPUtil.get(context, "id", "")), String.valueOf(SPUtil.get(context, "id", "")), new EMCallBack() {//回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
                            Log.d("main", "登录聊天服务器成功！");
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
        connected = Utils.isConnected(context);
        getupdata();

        if (!IntentUtil.isBundleEmpty(getIntent())) {
            int thispage = getIntent().getIntExtra("thispage", 0);
            if (thispage == 4) {
                this.thispage = Other.ME;
            } else if (thispage == 1) {
                this.thispage = Other.PRODUCT;
            }
        }

        changecheck(thispage);
        Acp.getInstance(context).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .setDeniedMessage(getString(R.string.requstPerminssions))
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

    private void getupdata() {
        RequestQueue requestQueueversion = Volley.newRequestQueue(context);
        StringRequest stringRequestversion = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "version", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    VersionBean versionBean = new Gson().fromJson(decode, VersionBean.class);
                    if (String.valueOf(versionBean.getStu()).equals("1")) {
                        try {
                            SPUtil.putAndApply(context, "VersionBean", decode);
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

        if (connected) {
            requestQueueversion.add(stringRequestversion);
        } else {
            EasyToast.showShort(context, getString(R.string.Notconnect));

        }

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
            imgHome.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_index2));
            imgDemo.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_fangan1));

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
            imgHome.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_wode2));
            imgProduct.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_fangan1));
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
            imgHome.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_yanshi2));
            imgMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_fangan1));
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
            imgHome.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_chanpin2));
            imgScheme.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_fangan1));
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
            imgHome.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_index1));
            imgDemo.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_yanshi1));
            imgMe.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_wode1));
            imgProduct.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_chanpin1));
            imgScheme.setBackgroundDrawable(getResources().getDrawable(R.mipmap.i_fangan2));
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

    private boolean isExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByDoubleClick();
        }
        return false;
    }

    private void exitByDoubleClick() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, getResources().getString(R.string.Clicktheexitprogramagain), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;//取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }
}
