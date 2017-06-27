package com.zzcn77.android_app_company.Acitivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/22.
 */

public class LanguageActivity extends BaseActivity implements View.OnClickListener {
    //
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.cb_cn)
    CheckBox cbCn;
    @BindView(R.id.cb_en)
    CheckBox cbEn;
    @BindView(R.id.cb_auto)
    CheckBox cbAuto;
    @BindView(R.id.rl_auto)
    RelativeLayout rlAuto;
    @BindView(R.id.rl_cn)
    RelativeLayout rlCn;
    @BindView(R.id.rl_en)
    RelativeLayout rlEn;

    @Override
    protected int setthislayout() {
        return R.layout.language_layout;
    }

    @Override
    protected void initview() {
        String language = (String) SPUtil.get(context, "Lanuage", "");
        if (language.isEmpty()) {
            cbAuto.setChecked(true);
            cbCn.setChecked(false);
            cbEn.setChecked(false);
        }
        if (language.equals("cn")) {
            cbAuto.setChecked(false);
            cbEn.setChecked(false);
            cbCn.setChecked(true);
        }
        if (language.equals("en")) {
            cbEn.setChecked(true);
            cbAuto.setChecked(false);
            cbCn.setChecked(false);
        }
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        rlAuto.setOnClickListener(this);
        rlCn.setOnClickListener(this);
        rlEn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.rl_auto:
                EasyToast.showShort(context, "跟随系统");
                cbEn.setChecked(false);
                cbCn.setChecked(false);
                cbAuto.setChecked(true);
                SPUtil.remove(context, "Lanuage");
                break;
            case R.id.rl_cn:
                EasyToast.showShort(context, "简体中文");
                cbEn.setChecked(false);
                cbAuto.setChecked(false);
                cbCn.setChecked(true);
                SPUtil.putAndApply(context, "Lanuage", "cn");
                break;
            case R.id.rl_en:
                EasyToast.showShort(context, "English");
                cbAuto.setChecked(false);
                cbCn.setChecked(false);
                cbEn.setChecked(true);
                SPUtil.putAndApply(context, "Lanuage", "en");
                break;
        }
    }


}
