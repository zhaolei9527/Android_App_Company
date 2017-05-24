package com.zzcn77.android_app_company.Acitivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class ConsultActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected int setthislayout() {
        return R.layout.consult_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
