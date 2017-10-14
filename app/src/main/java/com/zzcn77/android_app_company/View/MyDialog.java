package com.zzcn77.android_app_company.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzcn77.android_app_company.R;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by 赵磊 on 2017/10/9.
 */

public class MyDialog extends Dialog implements View.OnClickListener {
    Context mContext;

    public MyDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        this.mContext = context;
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
                Toast.makeText(mContext, "微信", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.ll_wechatfriend:
                Toast.makeText(mContext, "朋友圈", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.ll_facebook:
                Toast.makeText(mContext, "facebook", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.ll_twitter:
                Toast.makeText(mContext, "twitter", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.ll_whatsapp:
                Toast.makeText(mContext, "whatsapp", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    private void showShareqq() {
        OnekeyShare oks = new OnekeyShare();
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setTitleUrl("http://www.baidu.com");
        oks.setText("text");
        oks.setTitle("标题");
        oks.setPlatform(QQ.NAME);
        oks.show(getContext());
    }

}