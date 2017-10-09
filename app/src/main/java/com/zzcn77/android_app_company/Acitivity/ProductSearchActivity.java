package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zzcn77.android_app_company.Bean.GoodsBean;
import com.zzcn77.android_app_company.Bean.Goods_ListsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.IntentUtil;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;
import com.zzcn77.android_app_company.View.PowersearchDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import in.srain.cube.views.GridViewWithHeaderAndFooter;
import static com.zzcn77.android_app_company.R.id.tv_foot_more;

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
    GridViewWithHeaderAndFooter swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(tv_foot_more)
    TextView tvFootMore;
    private int page = 1;
    private String keywords = "";
    private String cid = "";
    private String bid = "";
    private String px_id = "";
    private String start_price = "";
    private String end_price = "";
    private ProductSearchAdapter productSearchAdapter;
    private int position;
    private int scrolledX = 0;
    private int scrolledY = 0;
    private Dialog dialog;
    private int po;
    private BroadcastReceiver broadcastReceiver;
    private BroadcastReceiver receiver;
    private Intent intent;
    private Intent intent1;

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

    public int getScrollY() {
        View c = swipeTarget.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = swipeTarget.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    @Override
    protected void initListener() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!IntentUtil.isBundleEmpty(intent)) {
                    boolean inched = intent.getBooleanExtra("inched", false);
                    if (inched) {
                        if (productSearchAdapter != null) {
                            ArrayList<Goods_ListsBean.ResBean.GoodsBean> datas = productSearchAdapter.getDatas();
                            datas.get(po).setColl("1");
                            productSearchAdapter = new ProductSearchAdapter(context, datas);
                            swipeTarget.setAdapter(productSearchAdapter);
                        }
                    } else {
                        if (productSearchAdapter != null) {
                            ArrayList<Goods_ListsBean.ResBean.GoodsBean> datas = productSearchAdapter.getDatas();
                            datas.get(po).setColl("2");
                            productSearchAdapter = new ProductSearchAdapter(context, datas);
                            swipeTarget.setAdapter(productSearchAdapter);
                        }
                    }
                    if (swipeTarget != null)
                        swipeTarget.setSelection(scrolledY);
                }
            }
        };
        registerReceiver(receiver, new IntentFilter("notifyData"));

        //改变加载显示的颜色
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        imgPowerSearch.setOnClickListener(this);
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

        dialog = Utils.showLoadingDialog(context);
        dialog.show();

        swipeTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                po = position;
                String item = productSearchAdapter.getItem(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", item);
                startActivity(intent);
            }
        });
        swipeTarget.setOnScrollListener(new AbsListView.OnScrollListener()

        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrolledY != 0) {
                    scrolledY = view.getFirstVisiblePosition() + 2;
                } else {
                    scrolledY = 1;
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

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()

        {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {

                    String content = etSearch.getText().toString().trim();
                    if (content.isEmpty()) {
                        EasyToast.showShort(context, etSearch.getHint().toString().trim());
                        return false;
                    }
                    dialog.show();
                    keywords = content;
                    swipeTarget.setAdapter(null);
                    page = 1;
                    cid = "";
                    bid = "";
                    px_id = "";
                    start_price = "";
                    end_price = "";
                    scrolledY = 0;
                    initData();
                }
                return false;
            }
        });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!IntentUtil.isBundleEmpty(intent)) {
                    etSearch.setText("");
                    page = 1;
                    keywords = "";
                    cid = "";
                    bid = "";
                    px_id = "";
                    start_price = "";
                    end_price = "";
                    cid = intent.getStringExtra("cid");
                    bid = intent.getStringExtra("bid");
                    px_id = intent.getStringExtra("px_id");
                    start_price = intent.getStringExtra("start_price");
                    end_price = intent.getStringExtra("end_price");
                    scrolledY = -2;
                }
                if (swipeToLoadLayout != null) {
                    swipeToLoadLayout.setLoadMoreEnabled(true);
                }
                if (tvFootMore != null) {
                    tvFootMore.setText(getString(R.string.uploading));
                    tvFootMore.setVisibility(View.VISIBLE);
                }
                if (dialog != null)
                    dialog.show();
                if (swipeTarget != null)
                    swipeTarget.setAdapter(null);
                initData();
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("PoswerSearch"));


    }

    //上拉加载
    @Override
    public void onLoadMore() {
        if (SwipeRefreshLayout != null)
            SwipeRefreshLayout.setEnabled(false);
        if (swipeTarget != null)
            swipeTarget.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = page + 1;
                    initData();
                    if (tvFootMore != null) {
                        tvFootMore.setVisibility(View.INVISIBLE);
                    }
                }
            }, 0);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        if (swipeToLoadLayout != null) {
            swipeToLoadLayout.setLoadMoreEnabled(true);
        }
        if (tvFootMore != null) {
            tvFootMore.setText(getString(R.string.uploading));
            tvFootMore.setVisibility(View.VISIBLE);
        }
        if (SwipeRefreshLayout != null)
            SwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = 1;
                    scrolledY = 1;
                    initData();
                }
            }, 0);
    }

    @Override
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "goods_lists", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (swipeTarget != null)
                    swipeTarget.setEnabled(true);
                if (decode.isEmpty()) {
                    if (swipeToLoadLayout != null)
                        swipeToLoadLayout.setLoadingMore(false);
                    if (SwipeRefreshLayout != null)
                        SwipeRefreshLayout.setRefreshing(false);
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                    if (tvFootMore != null) {
                        tvFootMore.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (dialog.isShowing()) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                    if (decode.contains("code\":\"111\"")) {
                        if (page == 1) {
                            if (llEmpty != null)
                                llEmpty.setVisibility(View.VISIBLE);
                            if (tvFootMore != null) {
                                tvFootMore.setVisibility(View.GONE);
                                tvFootMore.setText(getResources().getString(R.string.NOTMORE));
                            }
                        } else {
                            if (context != null)
                                Toast.makeText(context, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                            page = page - 1;
                            if (tvFootMore != null) {
                                tvFootMore.setVisibility(View.VISIBLE);
                                tvFootMore.setText(getResources().getString(R.string.NOTMORE));
                            }
                        }
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeToLoadLayout.setLoadMoreEnabled(false);
                        }

                        if (SwipeRefreshLayout != null)
                            SwipeRefreshLayout.setEnabled(true);
                        return;
                    }
                    if (llEmpty != null) {
                        llEmpty.setVisibility(View.GONE);
                    }
                    Goods_ListsBean goods_listsBean = new Gson().fromJson(decode, Goods_ListsBean.class);
                    if (goods_listsBean.getStu().equals("1")) {
                        if (goods_listsBean.getRes().getGoods().size() < 10) {
                            if (tvFootMore != null) {
                                tvFootMore.setVisibility(View.VISIBLE);
                                tvFootMore.setText(getResources().getString(R.string.NOTMORE));
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadingMore(false);
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }
                        } else {
                            if (swipeToLoadLayout != null)
                                swipeToLoadLayout.setLoadMoreEnabled(true);
                            if (tvFootMore != null) {
                                tvFootMore.setText(getString(R.string.uploading));
                                tvFootMore.setVisibility(View.VISIBLE);
                            }
                        }

                        if (tvFootMore != null) {
                            tvFootMore.setVisibility(View.VISIBLE);
                        }
                        if (page == 1) {
                            if (swipeTarget != null) {
                                productSearchAdapter = new ProductSearchAdapter(context, (ArrayList) goods_listsBean.getRes().getGoods());
                                swipeTarget.setAdapter(productSearchAdapter);
                                SwipeRefreshLayout.setRefreshing(false);
                            }
                        } else {
                            if (productSearchAdapter != null) {
                                productSearchAdapter.setDatas((ArrayList) goods_listsBean.getRes().getGoods());
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadingMore(false);
                                if (SwipeRefreshLayout != null)
                                    SwipeRefreshLayout.setEnabled(true);
                            }
                        }
                        if (swipeTarget != null)
                            swipeTarget.setSelection(scrolledY);
                        if (SwipeRefreshLayout != null) {
                            if (SwipeRefreshLayout.isRefreshing()) {
                                SwipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    } else {
                        if (swipeToLoadLayout != null)
                            swipeToLoadLayout.setLoadingMore(false);
                        if (SwipeRefreshLayout != null)
                            SwipeRefreshLayout.setRefreshing(false);
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Abnormalserver));
                        if (tvFootMore != null) {
                            tvFootMore.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                if (swipeToLoadLayout != null)
                    swipeToLoadLayout.setLoadingMore(false);
                if (SwipeRefreshLayout != null)
                    SwipeRefreshLayout.setRefreshing(false);
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                if (tvFootMore != null) {
                    tvFootMore.setVisibility(View.VISIBLE);
                }
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
            if (swipeToLoadLayout != null)
                swipeToLoadLayout.setLoadingMore(false);
            if (SwipeRefreshLayout != null)
                SwipeRefreshLayout.setRefreshing(false);
            if (context != null)
                EasyToast.showShort(context, getString(R.string.Notconnect));
            if (tvFootMore != null) {
                tvFootMore.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra("thispage",1);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_search:
                String content = etSearch.getText().toString().trim();
                if (content.isEmpty()) {
                    EasyToast.showShort(context, etSearch.getHint().toString().trim());
                    return;
                }
                dialog.show();
                keywords = content;
                swipeTarget.setAdapter(null);
                page = 1;
                cid = "";
                bid = "";
                px_id = "";
                start_price = "";
                end_price = "";
                scrolledY = 0;
                initData();
                break;
            case R.id.img_power_search:
                dialog.dismiss();
                String product = (String) SPUtil.get(context, "product", "");
                if (!product.isEmpty()) {
                    GoodsBean goodsBean = new Gson().fromJson(product, GoodsBean.class);
                    new PowersearchDialog.Builder(context, goodsBean).create().show();
                }
                break;
            case R.id.img_back:
                intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("thispage",1);
                startActivity(intent1);
                finish();
                break;
        }
    }

}
