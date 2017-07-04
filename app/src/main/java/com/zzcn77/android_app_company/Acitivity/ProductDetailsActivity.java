package com.zzcn77.android_app_company.Acitivity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.DocollBean;
import com.zzcn77.android_app_company.Bean.Goods_NYBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Service.DownloadPDF;
import com.zzcn77.android_app_company.Utils.CallPhoneUtils;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taobao.accs.ACCSManager.mContext;
import static com.zzcn77.android_app_company.R.id.rl_download;
import static com.zzcn77.android_app_company.Service.DownloadPDF.DOWNLOAD_PATH;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.forum_context)
    WebView forumContext;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.cb_collect)
    CheckBox cbCollect;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.rl_download)
    RelativeLayout rlDownload;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.ll_money)
    LinearLayout llMoney;
    @BindView(R.id.tv_showmoney)
    TextView tvShowmoney;
    @BindView(R.id.ll_showmoney)
    LinearLayout llShowmoney;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.rl_call_phone)
    RelativeLayout rlCallPhone;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rmb)
    TextView rmb;

    private int isDownload = 0;
    private Dialog dialog;
    private String stu;
    private Goods_NYBean goods_nyBean;
    private BroadcastReceiver receiver;
    private Intent intent;
    private File file;
    private String id;

    @Override
    protected int setthislayout() {
        return R.layout.product_details_layout;
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        IX5WebViewExtension ix5 = forumContext.getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }

        if (SPUtil.get(context, "id", "").equals("")) {
            llMoney.setVisibility(View.GONE);
            llShowmoney.setVisibility(View.VISIBLE);
            tvShowmoney.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            tvShowmoney.getPaint().setAntiAlias(true);//抗锯齿
        } else {
            llShowmoney.setVisibility(View.GONE);
            llMoney.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void initListener() {
        llShowmoney.setOnClickListener(this);
        SimpleDraweeView.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        rlCallPhone.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        rlDownload.setOnClickListener(this);
        forumContext.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                int w = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                int h = View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED);
                //重新测量
                webView.measure(w, h);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();
            }
        });
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!IntentUtil.isBundleEmpty(intent)) {
                    if (rlDownload != null) {
                        rlDownload.setEnabled(true);
                    }
                    if (tvDownload != null)
                        tvDownload.setText(R.string.openfile);
                    if (rlDownload != null)
                        rlDownload.setBackgroundColor(getResources().getColor(R.color.purple_progress));
                    isDownload = 1;
                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter("pdfisdownloading"));
    }

    @Override
    protected void initData() {

        final Intent intent = getIntent();
        if (!IntentUtil.isBundleEmpty(intent)) {
            id = intent.getStringExtra("id");
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "goods_ny", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        if (rlDownload != null) {
                            rlDownload.setEnabled(true);
                        }
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                    } else {
                        goods_nyBean = new Gson().fromJson(decode, Goods_NYBean.class);
                        if (goods_nyBean.getStu().equals("1")) {
                            String coll = goods_nyBean.getRes().getColl();
                            if (coll.equals("1")) {
                                if (cbCollect != null)
                                    cbCollect.setChecked(true);
                            } else if (coll.equals("-1")) {
                                if (cbCollect != null)
                                    cbCollect.setChecked(false);
                            }
                            File dir = new File(DOWNLOAD_PATH);
                            if (!dir.exists()) {
                                dir.mkdir();
                            }
                            String fileName = goods_nyBean.getRes().getPdf().substring(goods_nyBean.getRes().getPdf().lastIndexOf("/") + 1, goods_nyBean.getRes().getPdf().length());
                            if (fileName == null && TextUtils.isEmpty(fileName) && !fileName.contains(".pdf")) {
                                fileName = getPackageName() + ".pdf";
                            }
                            file = new File(dir + "/" + fileName);
                            if (file.exists()) {

                                if (tvDownload != null)
                                    tvDownload.setText(getString(R.string.openfile));
                                if (rlDownload != null)
                                    rlDownload.setBackgroundColor(getResources().getColor(R.color.purple_progress));
                                isDownload = 1;
                            }
                            if (forumContext != null)
                                forumContext.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        rmb.setText(goods_nyBean.getRes().getRmb());
                                        tvTitle.setText(goods_nyBean.getRes().getTitle());
                                        tvModel.setText(goods_nyBean.getRes().getX_num());
                                        tvPhone.setText(goods_nyBean.getRes().getTel());
                                        tvPrice.setText(goods_nyBean.getRes().getPrice());
                                        SimpleDraweeView.setImageURI(UrlUtils.BaseImg + goods_nyBean.getRes().getImg_lb());
                                        String decode = Utils.decode(goods_nyBean.getRes().getContent());
                                        Spanned spanned = Html.fromHtml(decode);
                                        Utils.inSetWebView(spanned.toString(), forumContext, context);
                                    }
                                });
                        } else {
                            if (rlDownload != null) {
                                rlDownload.setEnabled(true);
                            }
                            if (context != null)
                                EasyToast.showShort(context, getString(R.string.Abnormalserver));
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    if (rlDownload != null) {
                        rlDownload.setEnabled(true);
                    }
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("key", UrlUtils.key);
                    map.put("id", id);
                    if (!String.valueOf(SPUtil.get(context, "id", "")).toString().isEmpty()) {
                        map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                    }
                    return map;
                }
            };

            boolean connected = Utils.isConnected(context);
            if (connected) {
                requestQueue.add(stringRequest);
            } else {
                if (rlDownload != null) {
                    rlDownload.setEnabled(true);
                }
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Notconnect));
            }

        } else {
            if (rlDownload != null) {
                rlDownload.setEnabled(true);
            }
            if (context != null)
                Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();
        }
    }

    //判断某一个类是否存在任务栈里面
    private boolean isExistMainActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        ComponentName cmpName = intent.resolveActivity(getPackageManager());
        boolean flag = false;
        if (cmpName != null) { // 说明系统中存在这个activity
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);  //获取从栈顶开始往下查找的10个activity
            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
                    flag = true;
                    break;  //跳出循环，优化效率
                }
            }
        }
        return flag;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_showmoney:
                new AlertDialog.Builder(context).setTitle(R.string.message)//设置对话框标题
                        .setMessage(R.string.Youarenotcurrentlyloggedin)//设置显示的内容
                        .setPositiveButton(R.string.loginnow, new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                startActivity(new Intent(context, LoginActivity.class));
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        dialog.dismiss();
                    }
                }).show();//在按键响应事件中显示此对话框
                break;
            case R.id.SimpleDraweeView:
                if (goods_nyBean != null) {
                    Intent intent1 = new Intent(context, BigImageActivity.class);
                    intent1.putExtra("imgurl", UrlUtils.BaseImg + goods_nyBean.getRes().getImg_lb());
                    startActivity(intent1);
                }
                break;
            case R.id.img_back:
                boolean existMainActivity = isExistMainActivity(MainActivity.class);
                if (existMainActivity) {
                    finish();
                } else {
                    finish();
                    startActivity(new Intent(context, MainActivity.class));
                }
                break;
            case R.id.rl_call_phone:
                CallPhoneUtils.CallPhone(ProductDetailsActivity.this, tvPhone.getText().toString());
                break;
            case rl_download:
                if (goods_nyBean != null)
                    if (isDownload == 1) {
                        intent = new Intent(context, PdfActivity.class);
                        intent.putExtra("file", file.getAbsolutePath().toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, getString(R.string.downloading), Toast.LENGTH_SHORT).show();
                        Acp.getInstance(context).request(new AcpOptions.Builder()
                                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
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
                                        dialog.dismiss();
                                        Intent intent = new Intent(context, DownloadPDF.class);
                                        //apk下载地址
                                        intent.putExtra("url", UrlUtils.BaseImg + goods_nyBean.getRes().getPdf());
                                        intent.putExtra("id", id);
                                        context.startService(intent);
                                        tvDownload.setText(getString(R.string.downloading));
                                        rlDownload.setEnabled(false);
                                    }

                                    @Override
                                    public void onDenied(List<String> permissions) {
                                        Toast.makeText(mContext, getString(R.string.Thepermissionapplicationisrejected), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                break;
            case R.id.ll_collect:
                if (String.valueOf(SPUtil.get(context, "id", "")).isEmpty()) {
                    // TODO Auto-generated method stub
                    new AlertDialog.Builder(context).setTitle(R.string.message)//设置对话框标题
                            .setMessage(R.string.Youarenotcurrentlyloggedin)//设置显示的内容
                            .setPositiveButton(R.string.loginnow, new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                    startActivity(new Intent(context, LoginActivity.class));
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框
                } else {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "docoll", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            String decode = Utils.decode(s);
                            DocollBean docollBean = new Gson().fromJson(decode, DocollBean.class);
                            if (docollBean.getStu().equals("1")) {

                                if (cbCollect != null) {
                                    if (cbCollect.isChecked()) {
                                        if (context != null)
                                            Toast.makeText(context, R.string.Collectionofsuccess, Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (context != null)
                                            Toast.makeText(context, getString(R.string.cancelcollection), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                if (context != null)
                                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            volleyError.printStackTrace();
                            if (context != null)
                                EasyToast.showShort(context, getString(R.string.Networkexception));
                        }
                    })

                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("key", UrlUtils.key);
                            map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                            map.put("gid", String.valueOf(getIntent().getStringExtra("id")));
                            map.put("stu", stu);
                            return map;
                        }
                    };
                    boolean connected = Utils.isConnected(context);
                    if (connected) {
                        requestQueue.add(stringRequest);
                        if (cbCollect.isChecked()) {
                            stu = "2";
                        } else {
                            stu = "1";
                        }
                        if (cbCollect != null)
                            cbCollect.setChecked(!cbCollect.isChecked());
                        Intent intent = new Intent();
                        intent.setAction("notifyData");
                        if (cbCollect != null) {
                            if (cbCollect.isChecked()) {
                                intent.putExtra("inched", true);
                            } else {
                                intent.putExtra("inched", false);
                            }
                        }
                        sendBroadcast(intent);
                    } else {
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Notconnect));
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}