package com.zzcn77.android_app_company.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.zzcn77.android_app_company.Acitivity.ProductDetailsActivity;
import com.zzcn77.android_app_company.Acitivity.ProductSearchActivity;
import com.zzcn77.android_app_company.Adapter.GVProuctAdapter;
import com.zzcn77.android_app_company.Adapter.ProductAdapter;
import com.zzcn77.android_app_company.Bean.GoodsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;
import com.zzcn77.android_app_company.View.MyGridView;
import com.zzcn77.android_app_company.View.PowersearchDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class ProductFragment extends BaseFragment implements OnLoadMoreListener, View.OnClickListener, AdapterView.OnItemClickListener {
    //
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    MyGridView gvSwipeTarget;
    @BindView(R.id.img_power_search)
    ImageView imgPowerSearch;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    private ProductAdapter adapter;
    private int page = 1;
    private ProductAdapter productAdapter;
    private GVProuctAdapter gvProuctAdapter;
    private Intent intent;
    private GoodsBean goodsBean;
    private View foot;
    private View head;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_product_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        swipeToLoadLayout.setOnLoadMoreListener(this);
        head = View.inflate(mActivity, R.layout.product_head_layout, null);
        gvSwipeTarget = (MyGridView) head.findViewById(R.id.gv_swipe_target);
        foot = View.inflate(mActivity, R.layout.list_foot_layout, null);
        swipeTarget.addFooterView(foot, null, false);

        swipeTarget.addHeaderView(head);

        imgPowerSearch.setOnClickListener(this);
        String product = (String) SPUtil.get(mActivity, "product", "");
        if (!product.isEmpty()) {
            GoodsBean goodsBean = new Gson().fromJson(product, GoodsBean.class);
            productAdapter = new ProductAdapter(mActivity, (ArrayList) goodsBean.getRes().getGoodsmx());
            gvProuctAdapter = new GVProuctAdapter(mActivity, (ArrayList) goodsBean.getRes().getCate());
            gvSwipeTarget.setAdapter(gvProuctAdapter);
            swipeTarget.setAdapter(productAdapter);
        }
        imgSearch.setOnClickListener(this);
        swipeTarget.setOnItemClickListener(this);
        gvSwipeTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(mActivity, ProductSearchActivity.class);
                intent.putExtra("cid", gvProuctAdapter.getItem(position));
                startActivity(intent);
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
    }

    private void search() {
        String content = etSearch.getText().toString().trim();
        if (content.isEmpty()) {
            EasyToast.showShort(mActivity, etSearch.getHint().toString().trim());
            return;
        }
        Intent intent1 = new Intent(mActivity, ProductSearchActivity.class);
        intent1.putExtra("keywords", content);
        startActivity(intent1);
        mActivity.finish();
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "goods", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);
                        }
                        if (ProductFragment.this != null && ProductFragment.this.isAdded())
                            EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                        if (foot != null) {
                            foot.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (ProductFragment.this != null && ProductFragment.this.isAdded()) {
                            if (decode.contains("code\":\"111\"")) {
                                if (page == 1) {
                                    if (swipeTarget != null) {
                                        swipeTarget.setVisibility(View.INVISIBLE);
                                    }
                                    if (llEmpty != null)
                                        llEmpty.setVisibility(View.VISIBLE);
                                } else {
                                    if (ProductFragment.this != null && ProductFragment.this.isAdded()) {
                                        EasyToast.showShort(mActivity, getString(R.string.NOTMORE));
                                    }
                                    page = page - 1;
                                    if (foot != null) {
                                        foot.setVisibility(View.VISIBLE);
                                        TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                        tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                                        if (swipeToLoadLayout != null)
                                            swipeToLoadLayout.setLoadingMore(false);
                                        swipeToLoadLayout.setLoadMoreEnabled(false);
                                    }
                                }
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadingMore(false);

                                if (swipeTarget != null)
                                    swipeTarget.setEnabled(true);
                                return;
                            } else {

                                if (llEmpty != null) {
                                    llEmpty.setVisibility(View.GONE);
                                }
                                goodsBean = new Gson().fromJson(decode, GoodsBean.class);
                                if (goodsBean.getStu().equals("1")) {
                                    if (goodsBean.getRes().getGoodsmx().size() < 3) {
                                        if (foot != null) {
                                            foot.setVisibility(View.VISIBLE);
                                            TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                            tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                                            if (swipeToLoadLayout != null)
                                                swipeToLoadLayout.setLoadingMore(false);
                                            swipeToLoadLayout.setLoadMoreEnabled(false);
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
                                    if (foot != null) {
                                        foot.setVisibility(View.VISIBLE);
                                    }
                                    if (page == 1) {
                                        if (swipeTarget != null) {
                                            swipeTarget.setEnabled(true);
                                        }
                                        if (ProductFragment.this != null && ProductFragment.this.isAdded())
                                            SPUtil.putAndApply(mActivity, "product", decode);
                                        if (ProductFragment.this != null && ProductFragment.this.isAdded())
                                            productAdapter = new ProductAdapter(mActivity, (ArrayList) goodsBean.getRes().getGoodsmx());
                                        if (ProductFragment.this != null && ProductFragment.this.isAdded())
                                            gvProuctAdapter = new GVProuctAdapter(mActivity, (ArrayList) goodsBean.getRes().getCate());
                                        if (gvSwipeTarget != null) {
                                            gvSwipeTarget.setAdapter(gvProuctAdapter);
                                        }


                                        if (swipeTarget != null) {
                                            swipeTarget.setAdapter(productAdapter);
                                        }
                                    } else {
                                        productAdapter.setDatas((ArrayList) goodsBean.getRes().getGoodsmx());
                                        gvProuctAdapter.setDatas((ArrayList) goodsBean.getRes().getCate());
                                        if (swipeToLoadLayout != null) {
                                            swipeToLoadLayout.setLoadingMore(false);

                                        }
                                        if (swipeTarget != null) {
                                            swipeTarget.setEnabled(true);
                                        }
                                    }
                                    if (productAdapter != null) {
                                        productAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    if (swipeToLoadLayout != null) {
                                        swipeToLoadLayout.setLoadingMore(false);

                                    }
                                    if (ProductFragment.this != null && ProductFragment.this.isAdded())
                                        EasyToast.showShort(mActivity, getString(R.string.Abnormalserver));
                                    if (foot != null) {
                                        foot.setVisibility(View.VISIBLE);
                                    }
                                }
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
                    if (ProductFragment.this != null && ProductFragment.this.isAdded())
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
                    map.put("sh_id", String.valueOf(SPUtil.get(mActivity, "shid", "")));
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
                if (ProductFragment.this != null && ProductFragment.this.isAdded())
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
                initData(null);
                if (foot != null) {
                    foot.setVisibility(View.GONE);
                }

            }
        }, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_power_search:
                if (goodsBean != null) {
                    new PowersearchDialog.Builder(mActivity, goodsBean).create().show();
                }
                break;
            case R.id.img_search:
                search();
                break;


        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = productAdapter.getItem(position);
        Intent intent = new Intent(mActivity, ProductDetailsActivity.class);
        intent.putExtra("id", item);
        startActivity(intent);
    }


}
