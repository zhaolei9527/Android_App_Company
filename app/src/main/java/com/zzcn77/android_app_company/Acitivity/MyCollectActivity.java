package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.google.gson.Gson;
import com.zzcn77.android_app_company.Adapter.CollectAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.CollBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class MyCollectActivity extends BaseActivity implements View.OnClickListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.rl_checkall)
    RelativeLayout rlCheckall;
    @BindView(R.id.rl_deleteall)
    RelativeLayout rlDeleteall;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.rll_deleteall)
    RelativeLayout rllDeleteall;
    private CollectAdapter collectAdapter;
    private int page = 1;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.mycollect_layout;
    }

    @Override
    protected void initListener() {
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeTarget.setOnItemClickListener(this);
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
    }


    @Override
    protected void initData() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "coll", new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        EasyToast.showShort(context, "网络异常，请稍后再试");
                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (decode.contains("code\":\"111\"")) {
                            page = page - 1;
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeTarget.setEnabled(true);
                            if (collectAdapter == null) {
                                llEmpty.setVisibility(View.VISIBLE);
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                                return;
                            }
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        rllDeleteall.setVisibility(View.VISIBLE);
                        CollBean collBean = new Gson().fromJson(decode, CollBean.class);
                        if (collBean.getStu().equals("1")) {
                            if (page == 1) {
                                if (swipeTarget != null) {
                                    collectAdapter = new CollectAdapter(context, (ArrayList) collBean.getRes());
                                    swipeTarget.setAdapter(collectAdapter);
                                    swipeTarget.setEnabled(true);
                                }
                            } else {
                                collectAdapter.setDatas((ArrayList) collBean.getRes());
                                swipeToLoadLayout.setLoadingMore(false);
                                swipeTarget.setEnabled(true);
                            }
                            if (collectAdapter != null) {
                                collectAdapter.notifyDataSetChanged();
                            }
                        } else {
                            EasyToast.showShort(context, "服务器异常，请稍后再试");
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    EasyToast.showShort(context, "网络异常，请稍后再试");
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("key", UrlUtils.key);
                    map.put("p", String.valueOf(page));
                    map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                    return map;
                }
            };

            boolean connected = Utils.isConnected(context);
            if (connected) {
                requestQueue.add(stringRequest);
            } else {
                EasyToast.showShort(context, "网络异常，未连接网络");
            }
        } catch (Exception e) {
            // 可忽略的异常
        }


    }

    //上拉加载
    @Override
    public void onLoadMore() {
        swipeTarget.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1;
                initData();
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}
