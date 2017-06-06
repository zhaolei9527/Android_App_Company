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

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_product_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        swipeToLoadLayout.setOnLoadMoreListener(this);
        View head = View.inflate(mActivity, R.layout.product_head_layout, null);
        gvSwipeTarget = (MyGridView) head.findViewById(R.id.gv_swipe_target);
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
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "goods", new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                    } else {
                        if (decode.contains("code\":\"111\"")) {
                            if (page == 1) {
                                llEmpty.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(mActivity, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                                page = page - 1;
                            }
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeTarget.setEnabled(true);
                            return;
                        } else {
                            if (llEmpty != null) {
                                llEmpty.setVisibility(View.GONE);
                            }
                            goodsBean = new Gson().fromJson(decode, GoodsBean.class);
                            if (goodsBean.getStu().equals("1")) {
                                if (page == 1) {
                                    if (swipeTarget != null) {
                                        swipeTarget.setEnabled(true);
                                    }
                                    SPUtil.putAndApply(mActivity, "product", decode);
                                    productAdapter = new ProductAdapter(mActivity, (ArrayList) goodsBean.getRes().getGoodsmx());
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
                                    swipeToLoadLayout.setLoadingMore(false);
                                    swipeTarget.setEnabled(true);
                                }
                                if (productAdapter != null) {
                                    productAdapter.notifyDataSetChanged();
                                }
                            } else {
                                EasyToast.showShort(mActivity, getString(R.string.Abnormalserver));
                            }


                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    EasyToast.showShort(mActivity, getString(R.string.Networkexception));
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
                EasyToast.showShort(mActivity, getString(R.string.Notconnect));
            }
        } catch (Exception e) {
            // 可忽略的异常
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

            }
        }, 500);

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
