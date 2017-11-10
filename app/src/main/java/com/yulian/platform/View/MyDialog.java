package com.yulian.platform.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yulian.platform.Bean.VersionBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.twitter.Twitter;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.whatsapp.WhatsApp;

/**
 * Created by 赵磊 on 2017/10/9.
 */

public class MyDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    private VersionBean versionBean;

    private String code;
    private String name;
    private String img;
    private String text;

    public MyDialog(Context context, String code, String name, String img) {
        super(context, R.style.ActionSheetDialogStyle);
        this.mContext = context;
        this.code = code;
        this.name = name;
        this.img = img;

        String versionb = (String) SPUtil.get(context, "VersionBean", "");
        if (!versionb.isEmpty()) {
            versionBean = new Gson().fromJson(versionb, VersionBean.class);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_layout);
        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        LinearLayout ll_qq = (LinearLayout) findViewById(R.id.ll_qq);
        LinearLayout ll_wechat = (LinearLayout) findViewById(R.id.ll_wechat);
        LinearLayout ll_wechatfriend = (LinearLayout) findViewById(R.id.ll_wechatfriend);
        LinearLayout ll_facebook = (LinearLayout) findViewById(R.id.ll_facebook);
        LinearLayout ll_twitter = (LinearLayout) findViewById(R.id.ll_twitter);
        LinearLayout ll_whatsapp = (LinearLayout) findViewById(R.id.ll_whatsapp);
        tv_cancel.setOnClickListener(this);
        ll_qq.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_wechatfriend.setOnClickListener(this);
        ll_facebook.setOnClickListener(this);
        ll_twitter.setOnClickListener(this);
        ll_whatsapp.setOnClickListener(this);
        ll_facebook.setVisibility(View.GONE);
    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.ll_qq:
                showShareqq();
                dismiss();
                break;
            case R.id.ll_wechat:
                showShareWechat();
                dismiss();
                break;
            case R.id.ll_wechatfriend:
                showShareWechatfriend();
                dismiss();
                break;
            case R.id.ll_facebook:
                showSharefacebook();
                dismiss();
                break;
            case R.id.ll_twitter:
                showSharetwitter();
                dismiss();
                break;
            case R.id.ll_whatsapp:
                showSharewhatsapp();
                dismiss();
                break;
        }
    }

    private void showSharefacebook() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(name);
        text = name + getContext().getString(R.string.The_company_code) + ":" + code + getContext().getResources().getString(R.string.appdownload) + "http://yulian.u-linking.com/fenxiang/index";
        Log.d("MyDialog", text);
        oks.setText(text);
        oks.setImageUrl(UrlUtils.BaseImg + img);
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        oks.setPlatform(Facebook.NAME);
        oks.show(getContext());
    }

    private void showSharewhatsapp() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        text = name + getContext().getString(R.string.The_company_code) + ":" + code + getContext().getResources().getString(R.string.appdownload) + "http://yulian.u-linking.com/fenxiang/index";
        Log.d("MyDialog", text);
        oks.setText(text);
        oks.setPlatform(WhatsApp.NAME);
        oks.show(getContext());
    }

    private void showSharetwitter() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(name);
        text = name + getContext().getString(R.string.The_company_code) + ":" + code + getContext().getResources().getString(R.string.appdownload) + "http://yulian.u-linking.com/fenxiang/index";
        Log.d("MyDialog", text);
        oks.setText(text);
        oks.setImageUrl(UrlUtils.BaseImg + img);
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        oks.setPlatform(Twitter.NAME);
        oks.show(getContext());
    }

    private void showShareqq() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(name);
        oks.setTitleUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setText(getContext().getString(R.string.The_company_code) + ":" + code);
        oks.setImageUrl(UrlUtils.BaseImg + img);
        oks.setUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        oks.setSiteUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setPlatform(QQ.NAME);
        oks.show(getContext());
    }

    private void showShareWechat() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(name);
        oks.setTitleUrl("http://yulian.u-linking.com/fenxiang/index");
        text = name + "\n" + getContext().getString(R.string.The_company_code) + ":" + code;
        Log.d("MyDialog", text);
        oks.setText(text);
        oks.setImageUrl(UrlUtils.BaseImg + img);
        // TODO: 2017/11/8 此处填写下载连接
        oks.setUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        oks.setSiteUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setPlatform(Wechat.NAME);
        oks.show(getContext());
    }

    private void showShareWechatfriend() {
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(name);
        oks.setTitleUrl("http://yulian.u-linking.com/fenxiang/index");
        text = name + "\n" + getContext().getString(R.string.The_company_code) + ":" + code;
        Log.d("MyDialog", text);
        oks.setText(text);
        oks.setImageUrl(UrlUtils.BaseImg + img);
        oks.setUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        oks.setSiteUrl("http://yulian.u-linking.com/fenxiang/index");
        oks.setPlatform(WechatMoments.NAME);
        oks.show(getContext());
    }

}