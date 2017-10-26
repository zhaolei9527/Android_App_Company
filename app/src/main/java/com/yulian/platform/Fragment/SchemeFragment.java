package com.yulian.platform.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.yulian.platform.Acitivity.SchemeAcitivty;
import com.yulian.platform.Adapter.SchemeAdapter;
import com.yulian.platform.Bean.FangAnBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class SchemeFragment extends BaseFragment implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    //
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
    private View foot;

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
        foot = View.inflate(mActivity, R.layout.list_foot_layout, null);
        swipeTarget.addFooterView(foot, null, false);
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
                if (SwipeRefreshLayout != null) {
                    SwipeRefreshLayout.setEnabled(enable);
                }
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl22 + "fang", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);
                        }
                        if (SwipeRefreshLayout != null) {
                            SwipeRefreshLayout.setRefreshing(false);
                        }
                        if (SchemeFragment.this != null && SchemeFragment.this.isAdded())
                            EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                        if (foot != null)
                            foot.setVisibility(View.GONE);

                    } else {
                        if (decode.contains("code\":\"111\"")) {
                            if (SchemeFragment.this != null && SchemeFragment.this.isAdded()) {
                                EasyToast.showShort(mActivity, getString(R.string.NOTMORE));
                            }
                            page = page - 1;
                            if (SwipeRefreshLayout != null) {
                                SwipeRefreshLayout.setEnabled(true);
                            }
                            if (swipeToLoadLayout != null) {
                                swipeToLoadLayout.setLoadingMore(false);
                            }
                            if (swipeTarget != null) {
                                swipeTarget.setEnabled(true);
                            }
                            if (foot != null) {
                                foot.setVisibility(View.VISIBLE);
                                TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadingMore(false);
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                            return;
                        }
                        fangAnBean = new Gson().fromJson(decode, FangAnBean.class);
                        if (fangAnBean.getStu().equals("1")) {
                            if (fangAnBean.getRes().size() < 10) {
                                if (foot != null) {
                                    foot.setVisibility(View.VISIBLE);
                                    TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                    if (SchemeFragment.this != null && SchemeFragment.this.isAdded())
                                        tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                                    if (swipeToLoadLayout != null) {
                                        swipeToLoadLayout.setLoadingMore(false);
                                        swipeToLoadLayout.setLoadMoreEnabled(false);
                                    }
                                }
                            } else {
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadMoreEnabled(true);
                                if (foot != null) {
                                    TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                    tv_foot_more.setText(getString(R.string.uploading));
                                    foot.setVisibility(View.VISIBLE);
                                }
                            }

                            if (foot != null)
                                foot.setVisibility(View.VISIBLE);

                            if (page == 1) {
                                if (swipeTarget != null) {
                                    SPUtil.putAndApply(mActivity, "scheme", decode);
                                    if (SchemeFragment.this != null && SchemeFragment.this.isAdded())
                                        schemeAdapter = new SchemeAdapter(mActivity, (ArrayList) fangAnBean.getRes());
                                    if (swipeTarget != null) {
                                        swipeTarget.setAdapter(schemeAdapter);
                                        swipeTarget.setEnabled(true);
                                    }
                                    if (SwipeRefreshLayout != null)
                                        SwipeRefreshLayout.setRefreshing(false);
                                }
                            } else {
                                if (schemeAdapter != null)
                                    schemeAdapter.setDatas((ArrayList) fangAnBean.getRes());
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadingMore(false);
                                if (swipeTarget != null)
                                    swipeTarget.setEnabled(true);
                                if (SwipeRefreshLayout != null)
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
                            if (swipeToLoadLayout != null) {
                                swipeToLoadLayout.setLoadingMore(false);

                            }
                            if (SwipeRefreshLayout != null) {
                                SwipeRefreshLayout.setRefreshing(false);
                            }

                            if (SchemeFragment.this != null && SchemeFragment.this.isAdded())
                                EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                            if (foot != null)
                                foot.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    if (swipeToLoadLayout != null) {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                    if (SwipeRefreshLayout != null) {
                        SwipeRefreshLayout.setRefreshing(false);
                    }
                    if (SchemeFragment.this != null && SchemeFragment.this.isAdded())
                        EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                    if (foot != null)
                        foot.setVisibility(View.VISIBLE);

                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("key", UrlUtils.key);
                    map.put("p", String.valueOf(page));
                    map.put("sid", String.valueOf(SPUtil.get(mActivity, "shid", "")));
                    return map;
                }
            };

            boolean connected = Utils.isConnected(mActivity);
            if (connected) {
                requestQueue.add(stringRequest);
            } else {
                if (swipeToLoadLayout != null) {
                    swipeToLoadLayout.setLoadingMore(false);
                }
                if (SwipeRefreshLayout != null) {
                    SwipeRefreshLayout.setRefreshing(false);
                }
                if (SchemeFragment.this != null && SchemeFragment.this.isAdded())
                    EasyToast.showShort(mActivity, getString(R.string.Notconnect));
                if (foot != null)
                    foot.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            // 可忽略的异常
            if (foot != null)
                foot.setVisibility(View.VISIBLE);

        }


    }

    //上拉加载
    @Override
    public void onLoadMore() {
        if (swipeTarget != null)
            swipeTarget.setEnabled(false);
        if (SwipeRefreshLayout != null)
            SwipeRefreshLayout.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = page + 1;
                    initData(null);
                }
            }, 0);
        if (foot != null)
            foot.setVisibility(View.GONE);

    }

    //下拉刷新

    @Override
    public void onRefresh() {
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.setLoadMoreEnabled(true);
        if (foot != null) {
            TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
            tv_foot_more.setText(getString(R.string.uploading));
            foot.setVisibility(View.VISIBLE);
        }
        if (swipeTarget != null)
            swipeTarget.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = 1;
                    initData(null);
                }
            }, 0);
    }


}
