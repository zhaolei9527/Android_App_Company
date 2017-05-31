package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
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
import com.zzcn77.android_app_company.Adapter.ProductSearchAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.Goods_ListsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/31.
 */

public class ProductSearchActivity extends BaseActivity implements View.OnClickListener, OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.img_power_search)
    ImageView imgPowerSearch;
    @BindView(R.id.swipe_target)
    GridView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private int page = 1;
    private String keywords = "";
    private String cid = "";
    private String bid = "";
    private String px_id = "";
    private String start_price = "";
    private String end_price = "";
    private ProductSearchAdapter productSearchAdapter;
    private int position;
    private int scrolledX;
    private int scrolledY;

    @Override
    protected int setthislayout() {
        return R.layout.product_list_layout;
    }

    @Override
    protected void initview() {
        if (!IntentUtil.isBundleEmpty(getIntent())) {

            keywords = getIntent().getStringExtra("keywords");
            if (keywords != null && !keywords.isEmpty()) {
                etSearch.setText(keywords);
            }
            cid = getIntent().getStringExtra("cid");
            bid = getIntent().getStringExtra("bid");
            px_id = getIntent().getStringExtra("px_id");
            start_price = getIntent().getStringExtra("start_price");
            end_price = getIntent().getStringExtra("end_price");

        }

    }


    @Override
    protected void initListener() {
        //改变加载显示的颜色
        imgSearch.setOnClickListener(this);
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
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
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = productSearchAdapter.getItem(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", item);
                startActivity(intent);

            }
        });
        swipeTarget.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    scrolledX = swipeTarget.getScrollX();
                    scrolledY = swipeTarget.getScrollY();
                }
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
                }
                return false;
            }
        });
    }

    //上拉加载
    @Override
    public void onLoadMore() {
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
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
            }
        }, 1000);
    }

    @Override
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "goods_lists", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, "网络异常，请稍后再试");
                } else {
                    if (decode.contains("code\":\"111\"")) {
                        if (page == 1) {
                            llEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                            page = page - 1;
                        }
                        swipeToLoadLayout.setLoadingMore(false);
                        SwipeRefreshLayout.setEnabled(true);
                        return;
                    }
                    llEmpty.setVisibility(View.GONE);
                    Goods_ListsBean goods_listsBean = new Gson().fromJson(decode, Goods_ListsBean.class);
                    if (goods_listsBean.getStu().equals("1")) {
                        if (page == 1) {
                            if (swipeTarget != null) {
                                productSearchAdapter = new ProductSearchAdapter(context, (ArrayList) goods_listsBean.getRes().getGoods());
                                swipeTarget.setAdapter(productSearchAdapter);
                                SwipeRefreshLayout.setRefreshing(false);
                            }
                        } else {
                            productSearchAdapter.setDatas((ArrayList) goods_listsBean.getRes().getGoods());
                            swipeToLoadLayout.setLoadingMore(false);
                            SwipeRefreshLayout.setEnabled(true);
                        }

                        if (productSearchAdapter != null) {
                            productSearchAdapter.notifyDataSetChanged();
                        }

                        if (SwipeRefreshLayout != null) {
                            if (SwipeRefreshLayout.isRefreshing()) {
                                SwipeRefreshLayout.setRefreshing(false);
                                swipeTarget.scrollTo(scrolledX, scrolledY);
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
                if (keywords != null && !keywords.isEmpty()) {
                    map.put("keywords", keywords);
                }
                if (cid != null && !cid.isEmpty()) {
                    map.put("cid", cid);
                }
                if (bid != null && !bid.isEmpty()) {
                    map.put("bid", bid);
                }
                if (px_id != null && !px_id.isEmpty()) {
                    map.put("px_id", px_id);
                }
                if (start_price != null && !start_price.isEmpty()) {
                    map.put("start_price", start_price);
                }
                if (end_price != null && !end_price.isEmpty()) {
                    map.put("end_price", end_price);
                }
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


    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                String content = etSearch.getText().toString().trim();
                if (content.isEmpty()) {
                    content = etSearch.getHint().toString().trim();
                }
                keywords = content;
                initData();
                break;
            case R.id.img_power_search:
                // new PowersearchDialog.Builder(context,goodsBean).create().show();
                break;
        }
    }

}
