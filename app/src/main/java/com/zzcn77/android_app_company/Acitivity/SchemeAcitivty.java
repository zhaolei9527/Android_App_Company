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
import com.zzcn77.android_app_company.Bean.FangAnDetailBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/22.
 */

public class SchemeAcitivty extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.forum_context)
    WebView forumContext;
    @BindView(R.id.sv)
    ScrollView sv;
    private Dialog dialog;
    private FangAnDetailBean fangAnDetailBean;

    @Override
    protected int setthislayout() {
        return R.layout.secheme_details_layout;
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
        SimpleDraweeView.setOnClickListener(this);
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
                if (context != null)
                    Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
        if (!IntentUtil.isBundleEmpty(intent)) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl3 + "f_detail", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                    } else {
                        fangAnDetailBean = new Gson().fromJson(decode, FangAnDetailBean.class);
                        if (fangAnDetailBean.getStu().equals("1")) {
                            if (tvTitle != null)
                                tvTitle.setText(fangAnDetailBean.getRes().getTitle());
                            if (SimpleDraweeView != null)
                                SimpleDraweeView.setImageURI(UrlUtils.BaseImg + fangAnDetailBean.getRes().getImgurl());
                            if (forumContext != null)
                                forumContext.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String decode = Utils.decode(fangAnDetailBean.getRes().getContent());
                                        Spanned spanned = Html.fromHtml(decode);
                                        Utils.inSetWebView(spanned.toString(), forumContext, context);
                                    }
                                });
                        } else {
                            if (context != null)
                                EasyToast.showShort(context, getString(R.string.Abnormalserver));
                        }
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
                    map.put("id", intent.getStringExtra("id"));
                    return map;
                }
            };

            boolean connected = Utils.isConnected(context);
            if (connected) {
                requestQueue.add(stringRequest);
            } else {
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Notconnect));
            }

        } else {
            if (context != null)
                Toast.makeText(context, getString(R.string.hasError), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.SimpleDraweeView:
                Intent intent = new Intent(context, BigImageActivity.class);
                intent.putExtra("imgurl",UrlUtils.BaseImg + fangAnDetailBean.getRes().getImgurl());
                startActivity(intent);
                break;
        }
    }

}