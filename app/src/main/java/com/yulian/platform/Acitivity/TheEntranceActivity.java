package com.yulian.platform.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yulian.platform.App;
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.ComeOn;
import com.yulian.platform.Bean.IndexBean;
import com.yulian.platform.Bean.SerShBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.View.VerticalTextview;
import com.yulian.platform.Volley.VolleyInterface;
import com.yulian.platform.Volley.VolleyRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TheEntranceActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_back_login)
    Button imgBackLogin;
    @BindView(R.id.img_my)
    ImageView imgMy;
    @BindView(R.id.et_daihao)
    EditText etDaihao;
    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.et_guanjianci)
    EditText etGuanjianci;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.img_zhantingdaohang_bg)
    FrameLayout imgZhantingdaohangBg;
    @BindView(R.id.SimpleDraweeView)
    SimpleDraweeView simpleDraweeView;
    @BindView(R.id.tv_content)
    VerticalTextview tvContent;
    @BindView(R.id.ll_more)
    LinearLayout llMore;
    @BindView(R.id.ll_addBusinessmen)
    LinearLayout llAddBusinessmen;
    @BindView(R.id.ll_addBusinessmen1)
    LinearLayout ll_addBusinessmen1;
    @BindView(R.id.bg)
    SimpleDraweeView bg;
    private ArrayList<String> titleList = new ArrayList<String>();
    private ComeOn comeOn;
    private Dialog dialog;
    private SerShBean serShBean;

    @Override
    protected void ready() {
        super.ready();
       /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected int setthislayout() {
        return R.layout.activity_the_entrance;
    }

    @Override
    protected void initview() {
        String entrancebg = (String) SPUtil.get(context, "entrancebg", "");
        bg.setImageURI(UrlUtils.BaseImg + entrancebg);

        String zhantingbg = (String) SPUtil.get(context, "zhantingbg", "");
        simpleDraweeView.setImageURI(UrlUtils.BaseImg + zhantingbg);

    }

    @Override
    protected void initListener() {
        imgBackLogin.setOnClickListener(this);
        imgMy.setOnClickListener(this);
        llMore.setOnClickListener(this);
        btnGo.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        imgZhantingdaohangBg.setOnClickListener(this);
        //tvContent.setText(26, 5, Color.RED);//设置属性
        tvContent.setTextStillTime(3000);//设置停留时长间隔
        tvContent.setAnimTime(150);//设置进入和退出的时间间隔
        tvContent.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(context, NewsDetailsActivity.class).putExtra("id", comeOn.getRes().getNews().get(position).getId()));
            }
        });
    }

    @Override
    public void onResume() {
        tvContent.startAutoScroll();
        String usertype = (String) SPUtil.get(context, "usertype", "");
        if ("2".equals(usertype)) {
            finish();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        tvContent.stopAutoScroll();
        App.queues.cancelAll("come_on");
        App.queues.cancelAll("ser_sh");
        super.onPause();
    }

    @Override
    protected void initData() {
        String id = (String) SPUtil.get(context, "id", "");
        dialog = Utils.showLoadingDialog(context);
        dialog.show();

        if (!id.isEmpty()) {
            imgBackLogin.setVisibility(View.GONE);
        } else {
            imgBackLogin.setVisibility(View.VISIBLE);
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("key", UrlUtils.key);
        params.put("type", "1");
        if (!id.isEmpty()) {
            params.put("uid", id);
        }
        VolleyRequest.RequestPost(context, UrlUtils.BaseUrl21 + "come_on", "come_on", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = Utils.decode(result);
                Log.d("TheEntranceActivity", decode);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    if (decode.contains("会员信息不存在")) {
                        Toast.makeText(context, getString(R.string.Usernamedoesnotexist), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(context, LoginActivity.class));
                        finish();
                        return;
                    }
                    comeOn = new Gson().fromJson(decode, ComeOn.class);

                    bg.setImageURI(UrlUtils.BaseImg + comeOn.getRes().getBg_img());
                    SPUtil.putAndApply(context, "entrancebg", comeOn.getRes().getBg_img());

                    simpleDraweeView.setImageURI(UrlUtils.BaseImg + comeOn.getRes().getBeijingtu());
                    SPUtil.putAndApply(context, "zhantingbg", comeOn.getRes().getBeijingtu());


                    if (comeOn.getRes().getShanghu() != null) {
                        if (comeOn.getRes().getShanghu().size() == 1) {
                            View inflate = View.inflate(context, R.layout.rukou_item_company_layout, null);
                            LinearLayout ll_content = (LinearLayout) inflate.findViewById(R.id.ll_content);
                            TextView tv_rukou_company_title = (TextView) inflate.findViewById(R.id.tv_rukou_company_title);
                            TextView tv_rukou_company_daihao = (TextView) inflate.findViewById(R.id.tv_rukou_company_daihao);
                            TextView tv_rukou_company_hangye = (TextView) inflate.findViewById(R.id.tv_rukou_company_hangye);
                            TextView tv_rukou_company_pingfen = (TextView) inflate.findViewById(R.id.tv_rukou_company_pingfen);
                            SimpleDraweeView SimpleDraweeView = (SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
                            SimpleDraweeView.setImageURI(UrlUtils.BaseImg + comeOn.getRes().getCard_img());
                            tv_rukou_company_title.setText(comeOn.getRes().getShanghu().get(0).getName());
                            tv_rukou_company_daihao.setText(comeOn.getRes().getShanghu().get(0).getDaihao());
                            tv_rukou_company_hangye.setText(comeOn.getRes().getShanghu().get(0).getHangye());
                            tv_rukou_company_pingfen.setText(comeOn.getRes().getShanghu().get(0).getPoint());
                            ll_addBusinessmen1.addView(inflate);
                            inflate.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.show();
                                    getindex(comeOn.getRes().getShanghu().get(0).getId());
                                }
                            });

                        } else {
                            for (int i = 0; i < comeOn.getRes().getShanghu().size(); i++) {
                                View inflate = View.inflate(context, R.layout.rukou_item_company_layout, null);
                                LinearLayout ll_content = (LinearLayout) inflate.findViewById(R.id.ll_content);
                                SimpleDraweeView SimpleDraweeView = (SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
                                SimpleDraweeView.setImageURI(UrlUtils.BaseImg + comeOn.getRes().getCard_img());

                                TextView tv_rukou_company_title = (TextView) inflate.findViewById(R.id.tv_rukou_company_title);
                                TextView tv_rukou_company_daihao = (TextView) inflate.findViewById(R.id.tv_rukou_company_daihao);
                                TextView tv_rukou_company_hangye = (TextView) inflate.findViewById(R.id.tv_rukou_company_hangye);
                                TextView tv_rukou_company_pingfen = (TextView) inflate.findViewById(R.id.tv_rukou_company_pingfen);
                                tv_rukou_company_title.setText(comeOn.getRes().getShanghu().get(i).getName());
                                tv_rukou_company_daihao.setText(comeOn.getRes().getShanghu().get(i).getDaihao());
                                tv_rukou_company_hangye.setText(comeOn.getRes().getShanghu().get(i).getHangye());
                                tv_rukou_company_pingfen.setText(comeOn.getRes().getShanghu().get(i).getPoint());
                                llAddBusinessmen.addView(inflate);
                                final int finalI = i;
                                inflate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.show();
                                        getindex(comeOn.getRes().getShanghu().get(finalI).getId());
                                    }
                                });

                            }
                        }
                    }
                    if (comeOn.getRes().getNews() != null) {
                        for (int i = 0; i < comeOn.getRes().getNews().size(); i++) {
                            titleList.add(comeOn.getRes().getNews().get(i).getTitle());
                        }
                    }
                    tvContent.setTextList(titleList);
                    dialog.dismiss();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
            }
        });

    }

    private String account;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_zhantingdaohang_bg:
                startActivity(new Intent(context, TheExhibitionHallActivity.class));
                break;
            case R.id.img_back_login:
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                break;
            case R.id.img_my:
                account = (String) SPUtil.get(context, "account", "");
                if (account.trim().isEmpty()) {
                    Toast.makeText(context, getString(R.string.Youarenotcurrentlyloggedin), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, LoginActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(context, MyActivity.class));
                    finish();
                }
                break;
            case R.id.ll_more:
                SPUtil.remove(context, "shid");
                startActivity(new Intent(context, NewsActivity.class));
                break;
            case R.id.btn_search:
                if (etGuanjianci.getText().toString().isEmpty()) {
                    Toast.makeText(context, getString(R.string.Enter_Keywords), Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(context, BusinessmenSearchActivity.class).putExtra("guanjianci", etGuanjianci.getText().toString()));
                finish();
                break;
            case R.id.btn_go:
                if (etDaihao.getText().toString().isEmpty()) {
                    Toast.makeText(context, getString(R.string.Please_enter_a_company_code), Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();
                HashMap<String, String> params = new HashMap<>();
                params.put("key", UrlUtils.key);
                params.put("code", etDaihao.getText().toString());
                VolleyRequest.RequestPost(context, UrlUtils.BaseUrl21 + "ser_sh", "ser_sh", params, new VolleyInterface(context) {
                    @Override
                    public void onMySuccess(String result) {
                        String decode = Utils.decode(result);
                        Log.d("TheEntranceActivity", decode);
                        if (decode.isEmpty()) {
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                        } else {
                            if (decode.contains("122")) {
                                dialog.dismiss();
                                Toast.makeText(TheEntranceActivity.this, getString(R.string.Merchant_does_not_exist), Toast.LENGTH_SHORT).show();
                            } else {
                                serShBean = new Gson().fromJson(decode, SerShBean.class);
                                getindex(serShBean.getRes());
                            }
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        error.printStackTrace();
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    private void getindex(final String shid) {
        SPUtil.remove(context, "index");
        SPUtil.remove(context, "demo");
        SPUtil.remove(context, "product");
        SPUtil.remove(context, "scheme");

        SPUtil.putAndApply(context, "shid", String.valueOf(shid));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "index", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    dialog.dismiss();
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                } else {
                    IndexBean indexBean = new Gson().fromJson(decode, IndexBean.class);
                    if (indexBean.getStu().equals("1")) {
                        if (context != null) {
                            dialog.dismiss();
                            SPUtil.putAndApply(context, "index", s);
                            startActivity(new Intent(context, MainActivity.class));
                            finish();
                        }
                    } else {
                        dialog.dismiss();
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                volleyError.printStackTrace();
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("sh_id", shid);
                String id = (String) SPUtil.get(context, "id", "");
                if (!id.isEmpty()) {
                    map.put("uid", id);
                }
                return map;
            }
        };

        if (Utils.isConnected(context)) {
            requestQueue.add(stringRequest);
        } else {
            if (context != null) {
                EasyToast.showShort(context, getString(R.string.Notconnect));
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }

    }



}