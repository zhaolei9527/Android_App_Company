package com.zzcn77.android_app_company.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.zzcn77.android_app_company.Acitivity.SchemeAcitivty;
import com.zzcn77.android_app_company.Adapter.SchemeAdapter;
import com.zzcn77.android_app_company.Bean.FangAnBean;
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
 * Created by 赵磊 on 2017/5/17.
 */

public class SchemeFragment extends BaseFragment implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    private SchemeAdapter schemeAdapter;
    private int page = 1;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_scheme_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.text_blue));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        SwipeRefreshLayout.setOnRefreshListener(this);
        SwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (SwipeRefreshLayout != null) {
                    SwipeRefreshLayout.setRefreshing(true);
                }
            }
        });
        swipeTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(mActivity, SchemeAcitivty.class);
                intent.putExtra("id", schemeAdapter.getItem(position));
                startActivity(intent);
            }
        });
        swipeTarget.setOnScrollListener(new AbsListView.OnScrollListener() {

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
        });

        String scheme = (String) SPUtil.get(mActivity, "scheme", "");
        if (!scheme.isEmpty()) {
            fangAnBean = new Gson().fromJson(scheme, FangAnBean.class);
            schemeAdapter = new SchemeAdapter(mActivity, (ArrayList) fangAnBean.getRes());
            swipeTarget.setAdapter(schemeAdapter);
        }
    }

    private FangAnBean fangAnBean;

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl3 + "fang", new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                    } else {
                        if (decode.contains("code\":\"111\"")) {
                            Toast.makeText(mActivity, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                            page = page - 1;
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeTarget.setEnabled(true);
                            SwipeRefreshLayout.setEnabled(true);
                            return;
                        }
                        fangAnBean = new Gson().fromJson(decode, FangAnBean.class);
                        if (fangAnBean.getStu().equals("1")) {
                            if (page == 1) {
                                if (swipeTarget != null) {
                                    SPUtil.putAndApply(mActivity, "scheme", decode);
                                    schemeAdapter = new SchemeAdapter(mActivity, (ArrayList) fangAnBean.getRes());
                                    swipeTarget.setAdapter(schemeAdapter);
                                    swipeTarget.setEnabled(true);
                                    SwipeRefreshLayout.setRefreshing(false);
                                }
                            } else {
                                schemeAdapter.setDatas((ArrayList) fangAnBean.getRes());
                                swipeToLoadLayout.setLoadingMore(false);
                                swipeTarget.setEnabled(true);
                                SwipeRefreshLayout.setEnabled(true);
                            }
                            if (schemeAdapter != null) {
                                schemeAdapter.notifyDataSetChanged();
                            }
                            if (SwipeRefreshLayout != null) {
                                if (SwipeRefreshLayout.isRefreshing()) {
                                    SwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        } else {
                            EasyToast.showShort(mActivity,  getString(R.string.Networkexception));
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    EasyToast.showShort(mActivity,  getString(R.string.Networkexception));
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("key", UrlUtils.key);
                    map.put("p", String.valueOf(page));
                    return map;
                }
            };

            boolean connected = Utils.isConnected(mActivity);
            if (connected) {
                requestQueue.add(stringRequest);
            } else {
                EasyToast.showShort(mActivity,  getString(R.string.Notconnect));
            }
        } catch (Exception e) {
            // 可忽略的异常
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
                initData(null);
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

                initData(null);


            }
        }, 1000);
    }


}
