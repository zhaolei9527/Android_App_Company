package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.ConsultBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.zzcn77.android_app_company.R.id.btn_save;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class ConsultActivity extends BaseActivity implements View.OnClickListener {
    //
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
    @BindView(btn_save)
    Button btnSave;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.consult_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {

        imgBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);

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
            case btn_save:
                final String title = etTitle.getText().toString();
                final String content = etContent.getText().toString();
                final String email = etEmail.getText().toString();
                final String name = etName.getText().toString();
                final String phone = etPhone.getText().toString();

                if (title.trim().isEmpty()) {
                    Toast.makeText(context, etTitle.getHint().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (content.trim().isEmpty()) {
                    Toast.makeText(context, etContent.getHint().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phone.trim().isEmpty()) {
                    Toast.makeText(context, etPhone.getHint().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.trim().isEmpty()) {
                    Toast.makeText(context, etEmail.getHint().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!email.matches("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$")) {
                    EasyToast.showShort(context, getResources().getString(R.string.emailisnotregx));
                    return;
                }
                if (name.trim().isEmpty()) {
                    Toast.makeText(context, etName.getHint().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = Utils.showLoadingDialog(context);
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl2 + "consult", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String decode = Utils.decode(s);
                        if (decode.isEmpty()) {
                            dialog.dismiss();
                            if (context != null)
                                EasyToast.showShort(context, getString(R.string.Networkexception));

                        } else {
                            ConsultBean consultBean = new Gson().fromJson(decode, ConsultBean.class);
                            if (String.valueOf(consultBean.getStu()).equals("1")) {
                                // TODO: 2017/5/19 注册
                                dialog.dismiss();
                                if (context != null) {
                                    EasyToast.showShort(context, getString(R.string.submitsuccessfully));
                                    etName.setText("");
                                    etEmail.setText("");
                                    etContent.setText("");
                                    etPhone.setText("");
                                    etTitle.setText("");
                                }
                            } else {
                                dialog.dismiss();
                                if (context!=null){
                                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (dialog!=null){
                            dialog.dismiss();
                        }
                        volleyError.printStackTrace();
                        if (context!=null){
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("key", UrlUtils.key);
                        map.put("id", String.valueOf(SPUtil.get(context, "id", "")));
                        map.put("title", title);
                        map.put("content", content);
                        map.put("tel", phone);
                        map.put("email", email);
                        map.put("name", name);
                        return map;
                    }
                };

                boolean connected = Utils.isConnected(context);
                if (connected) {
                    requestQueue.add(stringRequest);
                } else {
                    if (dialog!=null){
                        dialog.dismiss();
                    }
                    if (context!=null){
                        EasyToast.showShort(context, getString(R.string.Notconnect));
                    }
                }

                break;

        }
    }
}
