package com.yulian.platform.Acitivity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
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
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.AdvertNyBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.DateUtil;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.IntentUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class NewsDetailsActivity extends BaseActivity implements View.OnClickListener {
    //
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

//    public class JavascriptInterface {
//        @android.webkit.JavascriptInterface
//        public void startPhotoActivity(String imageUrl) {
//            Intent intent = new Intent(NewsDetailsActivity.this, BigImageActivity.class);
//            intent.putExtra("imgurl", imageUrl);
//            startActivity(intent);
//        }
//    }
//
//    private String readJS() {
//        try {
//            InputStream inStream = getAssets().open("js.txt");
//            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//            byte[] bytes = new byte[1024];
//            int len = 0;
//            while ((len = inStream.read(bytes)) > 0) {
//                outStream.write(bytes, 0, len);
//            }
//            return outStream.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

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
        forumContext.getSettings().setJavaScriptEnabled(true);
        //    forumContext.addJavascriptInterface(new JavascriptInterface(), "mainActivity");
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
                // forumContext.loadUrl("javascript:(" + readJS() + ")()");

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

    }

    @Override
    protected void initData() {
        final Intent intent = getIntent();
        if (!IntentUtil.isBundleEmpty(intent)) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "advert_ny", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                    } else {
                        final AdvertNyBean advertNyBean = new Gson().fromJson(decode, AdvertNyBean.class);
                        if (advertNyBean.getStu().equals("1")) {
                            if (tvNewsTitle != null)
                                tvNewsTitle.setText(advertNyBean.getRes().getTitle());
                            if (tvTimeYear != null)
                                tvTimeYear.setText(DateUtil.getDay(Long.parseLong(advertNyBean.getRes().getAdd_time())));
                            if (tvTimeHour != null)
                                tvTimeHour.setText(DateUtil.getMillon(Long.parseLong(advertNyBean.getRes().getAdd_time())));
                            if (forumContext != null)
                                forumContext.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String decode = Utils.decode(advertNyBean.getRes().getContent());
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
                boolean existMainActivity = isExistMainActivity(MainActivity.class);
                boolean existTheEntranceActivity = isExistMainActivity(TheEntranceActivity.class);
                if (existMainActivity || existTheEntranceActivity) {
                    finish();
                } else {
                    finish();
                    startActivity(new Intent(context, MainActivity.class));
                }
                break;
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

}