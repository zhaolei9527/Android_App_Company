package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
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
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.AdvertNyBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.DateUtil;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv_news_title)
    TextView tvNewsTitle;
    @BindView(R.id.tv_releaseTime)
    TextView tvReleaseTime;
    @BindView(R.id.tv_time_year)
    TextView tvTimeYear;
    @BindView(R.id.tv_time_hour)
    TextView tvTimeHour;
    @BindView(R.id.forum_context)
    WebView forumContext;
    @BindView(R.id.sv)
    ScrollView sv;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.news_details_layout;
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

        forumContext.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

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

    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
        if (!IntentUtil.isBundleEmpty(intent)) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "advert_ny", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        EasyToast.showShort(context, "网络异常，请稍后再试");
                    } else {
                        final AdvertNyBean advertNyBean = new Gson().fromJson(decode, AdvertNyBean.class);
                        if (advertNyBean.getStu().equals("1")) {
                            tvNewsTitle.setText(advertNyBean.getRes().getTitle());
                            tvTimeYear.setText(DateUtil.getDay(Long.parseLong(advertNyBean.getRes().getAdd_time())));
                            tvTimeHour.setText(DateUtil.getMillon(Long.parseLong(advertNyBean.getRes().getAdd_time())));
                            forumContext.post(new Runnable() {
                                @Override
                                public void run() {
                                    String decode = Utils.decode(advertNyBean.getRes().getContent());
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
                startActivity(new Intent(context,MainActivity.class));
                break;
        }
    }

}