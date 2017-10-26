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

    private void showSharewhatsapp() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(name);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // text是分享文本，所有平台都需要这个字段
        text = name + "\n" + getContext().getString(R.string.The_company_code) + ":" + code + "\n" + getContext().getResources().getString(R.string.appdownload) + UrlUtils.BaseImg + versionBean.getRes().getAndroid();
        Log.d("MyDialog", text);
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.BaseImg + img);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        oks.setPlatform(WhatsApp.NAME);
// 启动分享GUI
        oks.show(getContext());
    }

    private void showSharetwitter() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(name);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // text是分享文本，所有平台都需要这个字段
        text = name + "\n" + getContext().getString(R.string.The_company_code) + ":" + code + "\n" + getContext().getResources().getString(R.string.appdownload) + UrlUtils.BaseImg + versionBean.getRes().getAndroid();
        Log.d("MyDialog", text);
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.BaseImg + img);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        oks.setPlatform(Twitter.NAME);
// 启动分享GUI
        oks.show(getContext());
    }

    private void showShareqq() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(name);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getContext().getString(R.string.The_company_code) + ":" + code);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.BaseImg + img);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        oks.setPlatform(QQ.NAME);
// 启动分享GUI
        oks.show(getContext());
    }

    private void showShareWechat() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(name);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getContext().getString(R.string.The_company_code) + ":" + code);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.BaseImg + img);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        oks.setPlatform(Wechat.NAME);
// 启动分享GUI
        oks.show(getContext());
    }

    private void showShareWechatfriend() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(name);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(getContext().getString(R.string.The_company_code) + ":" + code);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl(UrlUtils.BaseImg + img);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(getContext().getString(R.string.The_company_code) + ":" + code);
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(UrlUtils.BaseImg + versionBean.getRes().getAndroid());
        oks.setPlatform(WechatMoments.NAME);
// 启动分享GUI
        oks.show(getContext());
    }

}