package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zzcn77.android_app_company.Adapter.Demosadapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.YanShiBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class DemoSerachActivity extends BaseActivity implements AdapterView.OnItemClickListener, OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.swipe_target)
    GridView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    private int page = 1;
    Demosadapter demosadapter;
    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            boolean enable = false;
            if (swipeTarget != null && swipeTarget.getChildCount() > 0) {
                // check if the first item of the list is visible
                boolean firstItemVisible = swipeTarget.getFirstVisiblePosition() == 0;
                // check if the top of the first item is visible
                boolean topOfFirstItemVisible = swipeTarget.getChildAt(0).getTop() == 0;
                // enabling or disabling the refresh layout
                enable = firstItemVisible && topOfFirstItemVisible;
            }
            SwipeRefreshLayout.setEnabled(enable);
        }
    };
    private String title;
    private Dialog dialog;

    @Override
    protected int setthislayout() {
        return R.layout.demo_search_layout;
    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        SwipeRefreshLayout.setOnRefreshListener(this);
        swipeTarget.setOnItemClickListener(this);
        swipeTarget.setOnScrollListener(onScrollListener);
    }

    @Override
    public void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
    }

    @Override
    public void initData() {

        if (!IntentUtil.isBundleEmpty(getIntent())) {
            title = getIntent().getStringExtra("title");
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl3 + "yanshi", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String decode = Utils.decode(s);
                        if (decode.isEmpty()) {
                            EasyToast.showShort(context, "网络异常，请稍后再试");
                        } else {
                            if (decode.contains("code\":\"111\"")) {
                                page = page - 1;
                                if (swipeTarget != null) {
                                    swipeTarget.setEnabled(true);
                                }
                                if (SwipeRefreshLayout != null) {
                                    SwipeRefreshLayout.setEnabled(true);
                                    SwipeRefreshLayout.setRefreshing(false);
                                }
                                if (demosadapter == null) {
                                    dialog.dismiss();
                                    llEmpty.setVisibility(View.VISIBLE);
                                    if (swipeToLoadLayout != null) {
                                        swipeToLoadLayout.setLoadMoreEnabled(false);
                                    }
                                    return;
                                }
                                dialog.dismiss();
                                Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (swipeToLoadLayout != null) {
                                swipeToLoadLayout.setLoadMoreEnabled(true);
                            }
                            dialog.dismiss();
                            llEmpty.setVisibility(View.GONE);
                            YanShiBean yanShiBean = new Gson().fromJson(decode, YanShiBean.class);
                            if (yanShiBean.getStu().equals("1")) {
                                if (page == 1) {
                                    if (swipeTarget != null) {
                                        demosadapter = new Demosadapter(context, (ArrayList) yanShiBean.getRes());
                                        swipeTarget.setAdapter(demosadapter);
                                        swipeTarget.setEnabled(true);
                                        SwipeRefreshLayout.setRefreshing(false);
                                        swipeToLoadLayout.setLoadingMore(false);
                                    }
                                } else {
                                    demosadapter.setDatas((ArrayList) yanShiBean.getRes());
                                    swipeToLoadLayout.setLoadingMore(false);
                                    swipeTarget.setEnabled(true);
                                    SwipeRefreshLayout.setEnabled(true);
                                }


                                if (demosadapter != null) {
                                    demosadapter.notifyDataSetChanged();
                                }
                                if (SwipeRefreshLayout != null) {
                                    if (SwipeRefreshLayout.isRefreshing()) {
                                        SwipeRefreshLayout.setRefreshing(false);
                                    }
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
                        map.put("title", title);
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
            } finally {
            }
        } else {
            llEmpty.setVisibility(View.VISIBLE);
            Toast.makeText(context, "出错了", Toast.LENGTH_SHORT).show();
        }

    }

    //上拉加载
    @Override
    public void onLoadMore() {
        swipeTarget.setEnabled(false);
        SwipeRefreshLayout.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1;
                initData();
            }
        }, 1000);
    }

    //下拉刷新

    @Override
    public void onRefresh() {
        swipeTarget.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
            }
        }, 1000);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, DemoPlayActivity.class);
        intent.putExtra("id", demosadapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
