package com.zzcn77.android_app_company.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zzcn77.android_app_company.Acitivity.DemoPlayActivity;
import com.zzcn77.android_app_company.Acitivity.DemoSerachActivity;
import com.zzcn77.android_app_company.Adapter.Demosadapter;
import com.zzcn77.android_app_company.Bean.YanShiBean;
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
import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class DemoFragment extends BaseFragment implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {
    //
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.swipe_target)
    GridViewWithHeaderAndFooter swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    private Demosadapter demosadapter;
    private Intent intent;
    private int page = 1;
    private Intent intent1;
    private View foot;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_demo_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        //改变加载显示的颜色
        imgSearch.setOnClickListener(this);
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


        swipeTarget.setOnItemClickListener(this);
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

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });
        String demo = (String) SPUtil.get(mActivity, "demo", "");
        if (!demo.isEmpty()) {
            YanShiBean yanShiBean = new Gson().fromJson(demo, YanShiBean.class);
            demosadapter = new Demosadapter(mActivity, (ArrayList) yanShiBean.getRes());
            swipeTarget.setAdapter(demosadapter);
        }

    }

    public void search() {
        String content = etSearch.getText().toString().trim();
        if (content.isEmpty()) {
            EasyToast.showShort(mActivity, etSearch.getHint().toString().trim());
            return;
        }
        intent1 = new Intent(mActivity, DemoSerachActivity.class);
        intent1.putExtra("title", content);
        startActivity(intent1);
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl22 + "yanshi", new Response.Listener<String>() {
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
                        EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                        if (foot != null) {
                            foot.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (decode.contains("code\":\"111\"")) {
                            if (DemoFragment.this != null && DemoFragment.this.isAdded()) {
                                EasyToast.showShort(mActivity, getString(R.string.NOTMORE));
                            }
                            page = page - 1;
                            if (swipeToLoadLayout != null) {
                                swipeToLoadLayout.setLoadingMore(false);
                            }
                            if (swipeTarget != null) {
                                swipeTarget.setEnabled(true);
                            }
                            if (SwipeRefreshLayout != null) {
                                SwipeRefreshLayout.setEnabled(true);
                            }
                            if (foot != null) {
                                foot.setVisibility(View.VISIBLE);
                                TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                            }
                            if (swipeToLoadLayout != null) {
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                            return;
                        }
                        YanShiBean yanShiBean = new Gson().fromJson(decode, YanShiBean.class);
                        if (yanShiBean.getStu().equals("1")) {
                            if (DemoFragment.this != null && DemoFragment.this.isAdded()) {
                                if (yanShiBean.getRes().size() < 10) {
                                    if (foot != null) {
                                        foot.setVisibility(View.VISIBLE);
                                        TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                        if (tv_foot_more!=null){
                                            tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                                        }
                                        if (swipeToLoadLayout != null){
                                            swipeToLoadLayout.setLoadingMore(false);
                                            swipeToLoadLayout.setLoadMoreEnabled(false);
                                        }
                                    }
                                } else {
                                    if (swipeToLoadLayout != null)
                                        swipeToLoadLayout.setLoadMoreEnabled(true);
                                    if (foot != null) {
                                        TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                        if (DemoFragment.this != null && DemoFragment.this.isAdded())
                                            tv_foot_more.setText(getString(R.string.uploading));
                                        foot.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            if (DemoFragment.this != null && DemoFragment.this.isAdded()) {
                                if (page == 1) {
                                    if (swipeTarget != null) {
                                        SPUtil.putAndApply(mActivity, "demo", decode);
                                        demosadapter = new Demosadapter(mActivity, (ArrayList) yanShiBean.getRes());
                                        if (swipeTarget != null) {
                                            swipeTarget.setAdapter(demosadapter);
                                            swipeTarget.setEnabled(true);
                                        }
                                        if (SwipeRefreshLayout != null) {
                                            SwipeRefreshLayout.setRefreshing(false);
                                        }
                                    }
                                } else {
                                    demosadapter.setDatas((ArrayList) yanShiBean.getRes());
                                    if (swipeToLoadLayout != null) {
                                        swipeToLoadLayout.setLoadingMore(false);
                                    }

                                    if (swipeTarget != null) {
                                        swipeTarget.setEnabled(true);
                                    }

                                    if (SwipeRefreshLayout != null) {
                                        SwipeRefreshLayout.setEnabled(true);
                                    }
                                }

                                if (demosadapter != null) {
                                    demosadapter.notifyDataSetChanged();
                                }
                                if (SwipeRefreshLayout != null) {
                                    if (SwipeRefreshLayout.isRefreshing()) {
                                        SwipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            }
                        } else {
                            if (swipeToLoadLayout != null) {
                                swipeToLoadLayout.setLoadingMore(false);

                            }
                            if (SwipeRefreshLayout != null) {
                                SwipeRefreshLayout.setRefreshing(false);
                            }
                            if (DemoFragment.this != null && DemoFragment.this.isAdded())
                                EasyToast.showShort(mActivity, getString(R.string.Abnormalserver));
                            if (foot != null) {
                                foot.setVisibility(View.VISIBLE);
                            }
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
                    if (DemoFragment.this != null && DemoFragment.this.isAdded())
                        EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                    if (foot != null) {
                        foot.setVisibility(View.VISIBLE);
                    }
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("key", UrlUtils.key);
                    map.put("p", String.valueOf(page));
                    map.put("sid", String.valueOf(SPUtil.get(mActivity,"shid","")));
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
                if (DemoFragment.this != null && DemoFragment.this.isAdded())
                    EasyToast.showShort(mActivity, getString(R.string.Notconnect));
                if (foot != null) {
                    foot.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            // 可忽略的异常
            if (swipeToLoadLayout != null) {
                swipeToLoadLayout.setLoadingMore(false);

            }
            if (SwipeRefreshLayout != null) {
                SwipeRefreshLayout.setRefreshing(false);
            }
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
        if (foot != null) {
            foot.setVisibility(View.GONE);
        }
    }

    //下拉刷新

    @Override
    public void onRefresh() {
        if (swipeTarget != null)
            swipeTarget.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = 1;
                    initData(null);
                    if (foot != null) {
                        TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                        tv_foot_more.setText(getString(R.string.uploading));
                        foot.setVisibility(View.VISIBLE);
                    }
                    if (swipeToLoadLayout != null) {
                        swipeToLoadLayout.setLoadMoreEnabled(true);
                    }
                }
            }, 0);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent(mActivity, DemoPlayActivity.class);
        intent.putExtra("id", demosadapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                search();
                break;
        }
    }
}