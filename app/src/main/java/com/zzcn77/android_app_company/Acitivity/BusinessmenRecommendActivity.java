package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zzcn77.android_app_company.Adapter.BusinessmenRecommendadapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.IndexBean;
import com.zzcn77.android_app_company.Bean.ShopsBean;
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

public class BusinessmenRecommendActivity extends BaseActivity implements android.view.View.OnClickListener, AdapterView.OnItemClickListener, OnLoadMoreListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.View)
    android.view.View View;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private View foot;
    private BusinessmenRecommendadapter businessmenRecommendadapter;
    private int page = 1;
    private Dialog dialog;
    private ShopsBean shopsBean;

    @Override
    protected int setthislayout() {
        return R.layout.activity_businessmen_recommend;
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        foot = View.inflate(context, R.layout.list_foot_layout, null);
        swipeTarget.addFooterView(foot, null, false);
    }

    @Override
    protected void initListener() {
        imgClear.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

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
            }
        });
        swipeTarget.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl22 + "shops", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                Log.d("BusinessmenRecommendAct", decode);
                if (decode.isEmpty()) {
                    if (swipeToLoadLayout != null) {
                        swipeToLoadLayout.setLoadingMore(false);
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
                        if (page != 1) {
                            page = page - 1;
                        } else {
                            page = 1;
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
                    shopsBean = new Gson().fromJson(decode, ShopsBean.class);
                    if (shopsBean.getStu().equals("1")) {
                        if (shopsBean.getRes().size() < 10) {
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
                            businessmenRecommendadapter = new BusinessmenRecommendadapter(context, (ArrayList) shopsBean.getRes());
                            if (swipeTarget != null)
                                swipeTarget.setAdapter(businessmenRecommendadapter);
                        } else {
                            if (businessmenRecommendadapter != null)
                                businessmenRecommendadapter.setDatas((ArrayList) shopsBean.getRes());
                        }
                        if (businessmenRecommendadapter != null)
                            businessmenRecommendadapter.notifyDataSetChanged();

                    } else {
                        if (swipeToLoadLayout != null) {
                            swipeToLoadLayout.setLoadingMore(false);

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
                if (!etSearch.getText().toString().isEmpty()) {
                    map.put("keyword", etSearch.getText().toString());
                }
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
            if (context != null)
                EasyToast.showShort(context, getString(R.string.Notconnect));
            if (foot != null)
                foot.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_clear:
                etSearch.setText("");
                break;
            case R.id.tv_search:
                if (etSearch.getText().toString().isEmpty()) {
                    Toast.makeText(context, "search", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.show();
                initData();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dialog.show();
        getindex(shopsBean.getRes().get(position).getId());
    }

    private void getindex(final String shid) {
        SPUtil.putAndApply(context, "shid", String.valueOf(shid));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "index", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    dialog.dismiss();
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                } else {
                    IndexBean indexBean = new Gson().fromJson(decode, IndexBean.class);
                    if (indexBean.getStu().equals("1")) {
                        if (context != null) {
                            dialog.dismiss();
                            SPUtil.putAndApply(context, "index", s);
                            startActivity(new Intent(context, MainActivity.class));
                        }
                    } else {
                        dialog.dismiss();
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                volleyError.printStackTrace();
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("sh_id", shid);
                String id = (String) SPUtil.get(context, "id", "");
                if (!id.isEmpty()) {
                    map.put("uid", id);
                }
                return map;
            }
        };

        if (Utils.isConnected(context)) {
            requestQueue.add(stringRequest);
        } else {
            if (context != null) {
                EasyToast.showShort(context, getString(R.string.Notconnect));
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }

    }


    //上拉加载
    @Override
    public void onLoadMore() {
        page = page + 1;
        if (swipeTarget != null)
            swipeTarget.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initData();
                    if (swipeToLoadLayout != null)
                        swipeToLoadLayout.setLoadingMore(false);
                    if (swipeTarget != null)
                        swipeTarget.setEnabled(true);
                }
            }, 0);
    }

}
