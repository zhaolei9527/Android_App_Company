package com.zzcn77.android_app_company.Acitivity;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
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
import com.zzcn77.android_app_company.View.ProgressButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.taobao.accs.ACCSManager.mContext;
import static com.zzcn77.android_app_company.R.id.btn_progress;
import static com.zzcn77.android_app_company.R.id.rl_call_phone;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class ProductDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
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
    @BindView(rl_call_phone)
    RelativeLayout rlCallPhone;
    @BindView(R.id.rl_download)
    RelativeLayout rlDownload;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(btn_progress)
    ProgressButton btnProgress;
    private Dialog dialog;
    private String stu;
    private Goods_NYBean goods_nyBean;
    private BroadcastReceiver receiver;

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
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        rlCallPhone.setOnClickListener(this);
        llCollect.setOnClickListener(this);
        btnProgress.setOnClickListener(this);
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
                Toast.makeText(context, "出错啦", Toast.LENGTH_SHORT).show();
            }
        });
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!IntentUtil.isBundleEmpty(intent)) {
                    int progress = intent.getIntExtra("progress",50);
                    btnProgress.updateProgress(progress);

                }
            }
        };
        context.registerReceiver(receiver, new IntentFilter("pdfisdownloading"));
    }

    @Override
    protected void initData() {

        final Intent intent = getIntent();
        if (!IntentUtil.isBundleEmpty(intent)) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "goods_ny", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        EasyToast.showShort(context, "网络异常，请稍后再试");
                    } else {
                        goods_nyBean = new Gson().fromJson(decode, Goods_NYBean.class);
                        if (goods_nyBean.getStu().equals("1")) {
                            forumContext.post(new Runnable() {
                                @Override
                                public void run() {
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
                    map.put("id", intent.getStringExtra("id"));
                    return map;
                }
            };

            boolean connected = Utils.isConnected(context);
            if (connected) {
                requestQueue.add(stringRequest);
            } else {
                EasyToast.showShort(context, "网络异常，未连接网络");
            }

        } else {
            Toast.makeText(context, "出错啦", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                startActivity(new Intent(context, MainActivity.class));
                break;
            case R.id.rl_call_phone:
                CallPhoneUtils.CallPhone(ProductDetailsActivity.this, tvPhone.getText().toString());
                break;
            case btn_progress:

                Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();

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
                                intent.putExtra("id", intent.getStringExtra("id"));
                                context.startService(intent);
                                tvDownload.setText("正在下载");
                            }

                            @Override
                            public void onDenied(List<String> permissions) {
                                Toast.makeText(mContext, "权限申请被拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.ll_collect:


                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "docoll", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String decode = Utils.decode(s);
                        DocollBean docollBean = new Gson().fromJson(decode, DocollBean.class);
                        if (docollBean.getStu().equals("1")) {
                            if (cbCollect.isChecked()) {
                                Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "已取消收藏", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            EasyToast.showShort(context, "服务器异常，请稍后再试");
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
                        map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                        map.put("gid", String.valueOf(getIntent().getStringExtra("id")));
                        map.put("stu", stu);
                        return map;
                    }
                };
                boolean connected = Utils.isConnected(context);
                if (connected) {
                    requestQueue.add(stringRequest);
                    cbCollect.setChecked(!cbCollect.isClickable());
                    if (cbCollect.isChecked()) {
                        stu = "1";
                    } else {
                        stu = "2";
                    }

                } else {
                    EasyToast.showShort(context, "网络异常，未连接网络");
                }

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}