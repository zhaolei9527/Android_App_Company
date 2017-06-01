package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
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

    @Override
    protected int setthislayout() {
        return R.layout.promotion_layout;
    }

    @Override
    protected void initview() {
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);

        dialog = Utils.showLoadingDialog(context);
        dialog.show();

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
                    Toast.makeText(context, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                    page = page - 1;
                    return;
                } else {
                    CuxiaoBean cuxiaoBean = new Gson().fromJson(decode, CuxiaoBean.class);
                    if (cuxiaoBean.getStu().equals("1")) {
                        if (page == 1) {
                            promotionListAdapter = new PromotionListAdapter(context, cuxiaoBean.getRes());
                            lvSwipeTarget.setAdapter(promotionListAdapter);
                        } else {
                            promotionListAdapter.setDatas((ArrayList) cuxiaoBean.getRes());
                        }
                        promotionListAdapter.notifyDataSetChanged();
                    } else {
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                EasyToast.showShort(context, getString(R.string.Networkexception));
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
            EasyToast.showShort(context, getString(R.string.Notconnect));
        }
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        lvSwipeTarget.setEnabled(false);
        SwipeRefreshLayout.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = page + 1;
                initData();
                swipeToLoadLayout.setLoadingMore(false);
                lvSwipeTarget.setEnabled(true);
                SwipeRefreshLayout.setEnabled(true);
            }
        }, 2000);

    }

    //下拉刷新

    @Override
    public void onRefresh() {
        lvSwipeTarget.setEnabled(false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
                lvSwipeTarget.setEnabled(true);
                SwipeRefreshLayout.setRefreshing(false);
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
        intent.putExtra("id",promotionListAdapter.getItem(position));
        startActivity(intent);
    }
}