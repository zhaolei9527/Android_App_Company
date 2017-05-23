package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.FrameLayout;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.TagUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/22.
 */

public class DemoPlayActivity extends BaseActivity {


    @BindView(R.id.webview_play)
    WebView forumContext;
    @BindView(R.id.videoContainer)
    FrameLayout mVideoContainer;
    private Dialog dialog;
    private IX5WebChromeClient.CustomViewCallback mCallBack;

    @Override
    protected void ready() {
        super.ready();

    }

    @Override
    protected int setthislayout() {
        return R.layout.demo_play_layout;
    }

    private class JsObject {

        @JavascriptInterface
        public void fullscreen() {
            //监听到用户点击全屏按钮
            fullScreen();
        }
    }

    private class CustomWebViewChromeClient extends WebChromeClient {

        @Override
        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
            fullScreen();
            forumContext.setVisibility(View.GONE);
            mVideoContainer.setVisibility(View.VISIBLE);
            mVideoContainer.addView(view);
            mCallBack = callback;
            super.onShowCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            fullScreen();
            if (mCallBack != null) {
                mCallBack.onCustomViewHidden();
            }
            forumContext.setVisibility(View.VISIBLE);
            mVideoContainer.removeAllViews();
            mVideoContainer.setVisibility(View.GONE);
            super.onHideCustomView();
        }
    }

    @Override
    protected void initview() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog = Utils.showLoadingDialog(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            forumContext.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        forumContext.addJavascriptInterface(new JsObject(), "onClick");
        forumContext.addJavascriptInterface(new JsObject(), "onClick");
        com.tencent.smtt.sdk.WebSettings webSettings = forumContext.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(com.tencent.smtt.sdk.WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        //  webSettings.setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        forumContext.post(new Runnable() {
            @Override
            public void run() {
                forumContext.loadUrl("http://m.bilibili.com/video/av9988478.html");
            }
        });
        forumContext.getSettings().setDomStorageEnabled(true);
        forumContext.setWebChromeClient(new CustomWebViewChromeClient());
        forumContext.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                }
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
                String js = TagUtils.getJs(s);
                webView.loadUrl(js);
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);

                Log.d("DemoPlayActivity", webResourceError.getDescription().toString());

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (forumContext.canGoBack()) {
            forumContext.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void fullScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            forumContext.getClass().getMethod("onResume").invoke(forumContext, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            forumContext.getClass().getMethod("onPause").invoke(forumContext, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}