package com.yulian.platform.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
import com.yulian.platform.Adapter.Newsadapter;
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.NewsBean;
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
 * Created by 赵磊 on 2017/5/19.
 */

public class NewsActivity extends BaseActivity implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemClickListener {
    //
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.swipe_target)
    ListView lvSwipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Newsadapter newsadapter;
    private int page = 1;
    private Intent intent;
    private Dialog dialog;
    private View foot;

    @Override
    protected int setthislayout() {
        return R.layout.promotion_layout;
    }

    @Override
    protected void initview() {
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.text_blue));
        SwipeRefreshLayout.setRefreshing(true);
        tvTitle.setText(R.string.recentnews);
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        foot = View.inflate(context, R.layout.list_foot_layout, null);
        lvSwipeTarget.addFooterView(foot, null, false);

    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
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

        lvSwipeTarget.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (lvSwipeTarget != null && lvSwipeTarget.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = lvSwipeTarget.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = lvSwipeTarget.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                SwipeRefreshLayout.setEnabled(enable);
            }
        });

        lvSwipeTarget.setOnItemClickListener(this);
    }


    @Override
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "advert", new Response.Listener<String>() {
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
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                    if (foot != null)
                        foot.setVisibility(View.VISIBLE);
                } else {
                    dialog.dismiss();
                    if (decode.contains("code\":\"111\"")) {
                        if (context != null)
                            Toast.makeText(context, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                        page = page - 1;
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
                    NewsBean newsBean = new Gson().fromJson(decode, NewsBean.class);
                    if (newsBean.getStu().equals("1")) {
                        if (newsBean.getRes().size() < 10) {
                            if (foot != null) {
                                foot.setVisibility(View.VISIBLE);
                                TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
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
                            newsadapter = new Newsadapter(context, (ArrayList) newsBean.getRes());

                            if (lvSwipeTarget != null)
                                lvSwipeTarget.setAdapter(newsadapter);
                        } else {
                            if (newsadapter != null)
                                newsadapter.setDatas((ArrayList) newsBean.getRes());
                        }
                        if (newsadapter != null)
                            newsadapter.notifyDataSetChanged();
                        if (SwipeRefreshLayout != null)
                            if (SwipeRefreshLayout.isRefreshing()) {
                                SwipeRefreshLayout.setRefreshing(false);
                            }
                    } else {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);

                        }
                        if (SwipeRefreshLayout != null) {
                            SwipeRefreshLayout.setRefreshing(false);
                        }
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Abnormalserver));
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
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                if (foot != null)
                    foot.setVisibility(View.VISIBLE);
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                if (!String.valueOf(SPUtil.get(context, "shid", "")).isEmpty()) {
                    map.put("sh_id", String.valueOf(SPUtil.get(context, "shid", "")));
                }
                map.put("p", String.valueOf(page));
                return map;
            }
        };

        boolean connected = Utils.isConnected(context);
        if (connected) {
            requestQueue.add(stringRequest);
        } else {
            if (swipeToLoadLayout != null) {
                swipeToLoadLayout.setLoadingMore(false);

            }
            if (SwipeRefreshLayout != null) {
                SwipeRefreshLayout.setRefreshing(false);
            }
            if (context != null)
                EasyToast.showShort(context, getString(R.string.Notconnect));
            if (foot != null)
                foot.setVisibility(View.VISIBLE);
        }


    }

    //上拉加载
    @Override
    public void onLoadMore() {
        page = page + 1;
        if (lvSwipeTarget != null)
            lvSwipeTarget.setEnabled(false);
        if (SwipeRefreshLayout != null)
            SwipeRefreshLayout.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                    if (SwipeRefreshLayout != null)
                        SwipeRefreshLayout.setEnabled(true);
                    if (swipeToLoadLayout != null)
                        swipeToLoadLayout.setLoadingMore(false);
                    if (lvSwipeTarget != null)
                        lvSwipeTarget.setEnabled(true);
                }
            }, 0);

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
        if (lvSwipeTarget != null)
            lvSwipeTarget.setEnabled(false);
        page = 1;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                if (lvSwipeTarget != null)
                    lvSwipeTarget.setEnabled(true);
                if (SwipeRefreshLayout != null)
                    SwipeRefreshLayout.setRefreshing(false);
                if (foot != null)
                    foot.setVisibility(View.GONE);
            }
        }, 0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }

    }

    //条目点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra("id", String.valueOf(newsadapter.getItem(position).getId()));
        startActivity(intent);

    }
}