package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
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
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.CompanyDetailsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.DensityUtils;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.Utils.VolleyLoadPicture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/18.
 */

public class CompanyDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.RollPagerView)
    com.jude.rollviewpager.RollPagerView RollPagerView;
    @BindView(R.id.forum_context)
    com.tencent.smtt.sdk.WebView forumContext;
    @BindView(R.id.img_back)
    ImageView imgBack;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.company_details_layout;
    }


    private class LoopAdapter extends LoopPagerAdapter {

        ArrayList<CompanyDetailsBean.ResBean.LunboBean> datas = new ArrayList();

        public LoopAdapter(com.jude.rollviewpager.RollPagerView viewPager, List datas) {
            super(viewPager);
            this.datas = (ArrayList<CompanyDetailsBean.ResBean.LunboBean>) datas;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            VolleyLoadPicture vlp = new VolleyLoadPicture(container.getContext(), view);
            vlp.getmImageLoader().get(UrlUtils.BaseImg + datas.get(position).getImg(), vlp.getOne_listener());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return datas.size();
        }
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        RollPagerView.setHintView(new IconHintView(context, R.drawable.shape_selected, R.drawable.shape_noraml, DensityUtils.dp2px(context, getResources().getDimension(R.dimen.x7))));
        RollPagerView.setPlayDelay(3000);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
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
                Toast.makeText(context, R.string.hasError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "jianjie", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    getCompanyDetails();

                    EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    CompanyDetailsBean companyDetailsBean = new Gson().fromJson(decode, CompanyDetailsBean.class);
                    if (companyDetailsBean.getStu().equals("1")) {
                        SPUtil.putAndApply(context, "jianjie", s);
                        getCompanyDetails();
                    } else {
                        getCompanyDetails();
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                getCompanyDetails();

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
            getCompanyDetails();
            EasyToast.showShort(context, getString(R.string.Notconnect));
        }

    }

    private void getCompanyDetails() {
        final CompanyDetailsBean jianjie = new Gson().fromJson(SPUtil.get(context, "jianjie", "").toString(), CompanyDetailsBean.class);
        forumContext.post(new Runnable() {
            @Override
            public void run() {
                String decode = Utils.decode(jianjie.getRes().getJianjie().getContent());
                Spanned spanned = Html.fromHtml(decode);
                Utils.inSetWebView(spanned.toString(), forumContext, context);
            }
        });
        RollPagerView.setAdapter(new LoopAdapter(RollPagerView,jianjie.getRes().getLunbo()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }

    }
}
