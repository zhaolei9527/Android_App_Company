package com.zzcn77.android_app_company.Acitivity;

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
import com.zzcn77.android_app_company.Adapter.PromotionListAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.CuxiaoBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by 赵磊 on 2017/5/19.
 */

public class PromotionActivity extends BaseActivity implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemClickListener {
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
    private int page = 1;
    private PromotionListAdapter promotionListAdapter;
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
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        foot = View.inflate(context, R.layout.list_foot_layout, null);
        lvSwipeTarget.addFooterView(foot);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl3 + "cuxiao", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                dialog.dismiss();
                if (decode.contains("code\":\"111\"")) {
                    if (swipeToLoadLayout != null) {
                        swipeToLoadLayout.setLoadingMore(false);
                    }
                    if (SwipeRefreshLayout != null) {
                        SwipeRefreshLayout.setRefreshing(false);
                    }
                    if (context != null) {
                        Toast.makeText(context, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                    }
                    if (foot != null) {
                        foot.setVisibility(View.VISIBLE);
                        TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                        tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                        if (swipeToLoadLayout != null)
                            swipeToLoadLayout.setLoadingMore(false);
                        swipeToLoadLayout.setLoadMoreEnabled(false);
                    }


                    page = page - 1;
                    return;
                } else {
                    CuxiaoBean cuxiaoBean = new Gson().fromJson(decode, CuxiaoBean.class);
                    if (cuxiaoBean.getStu().equals("1")) {
                        if (foot != null)
                            foot.setVisibility(View.VISIBLE);
                        if (page == 1) {
                            promotionListAdapter = new PromotionListAdapter(context, cuxiaoBean.getRes());
                            if (lvSwipeTarget != null)
                                lvSwipeTarget.setAdapter(promotionListAdapter);
                        } else {
                            if (promotionListAdapter != null)
                                promotionListAdapter.setDatas((ArrayList) cuxiaoBean.getRes());
                        }
                        if (promotionListAdapter != null)
                            promotionListAdapter.notifyDataSetChanged();
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
        if (lvSwipeTarget != null)
            lvSwipeTarget.setEnabled(false);
        if (SwipeRefreshLayout != null)
            SwipeRefreshLayout.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = page + 1;
                    initData();
                    if (SwipeRefreshLayout != null) {
                        swipeToLoadLayout.setLoadingMore(false);
                        SwipeRefreshLayout.setEnabled(true);
                    }
                    if (lvSwipeTarget != null) {
                        lvSwipeTarget.setEnabled(true);
                    }
                    if (foot != null)
                        foot.setVisibility(View.GONE);

                }
            }, 0);

    }

    //下拉刷新

    @Override
    public void onRefresh() {
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.setLoadMoreEnabled(true);
        if (foot!=null){
            TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
            tv_foot_more.setText(getString(R.string.uploading));
            foot.setVisibility(View.VISIBLE);
        }
        if (lvSwipeTarget != null)
            lvSwipeTarget.setEnabled(false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
                if (lvSwipeTarget != null) {
                    lvSwipeTarget.setEnabled(true);
                }
                if (SwipeRefreshLayout != null) {
                    SwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, PeomotionDetailsActivity.class);
        intent.putExtra("id", promotionListAdapter.getItem(position));
        startActivity(intent);
    }
}