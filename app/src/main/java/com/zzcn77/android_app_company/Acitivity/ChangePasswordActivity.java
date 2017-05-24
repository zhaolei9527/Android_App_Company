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

public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_oldpassword)
    EditText etOldpassword;
    @BindView(R.id.et_newpassword)
    EditText etNewpassword;
    @BindView(R.id.et_newpasswordagain)
    EditText etNewpasswordagain;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected int setthislayout() {
        return R.layout.change_password_layout;
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
