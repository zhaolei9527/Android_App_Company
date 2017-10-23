package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zzcn77.android_app_company.Adapter.BoothAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.BoothBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class ZhanweiSearchActivity extends BaseActivity implements TextView.OnEditorActionListener, android.view.View.OnClickListener, AdapterView.OnItemClickListener, OnLoadMoreListener {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.View)
    android.view.View View;
    @BindView(R.id.rl1)
    LinearLayout rl1;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private View foot;
    private BoothAdapter boothAdapter;
    private int page = 1;
    private Dialog dialog;
    private BoothBean boothBean;

    @Override
    protected int setthislayout() {
        return R.layout.activity_zhanwei_search;
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.dismiss();
        foot = View.inflate(context, R.layout.list_foot_layout, null);
        swipeTarget.addFooterView(foot, null, false);
    }

    @Override
    protected void initListener() {
        imgClear.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        etSearch.setOnEditorActionListener(this);
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

    }

    private void getdata() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl22 + "booth", new Response.Listener<String>() {
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
                    boothBean = new Gson().fromJson(decode, BoothBean.class);
                    if (boothBean.getStu().equals("1")) {
                        if (boothBean.getRes().size() < 10) {
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
                            boothAdapter = new BoothAdapter(context, (ArrayList) boothBean.getRes());
                            if (swipeTarget != null)
                                swipeTarget.setAdapter(boothAdapter);
                        } else {
                            if (boothAdapter != null)
                                boothAdapter.setDatas((ArrayList) boothBean.getRes());
                        }
                        if (boothAdapter != null)
                            boothAdapter.notifyDataSetChanged();

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
                } else {
                    map.put("keyword", "1");
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
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(context, ExhibitionDetailsActivity.class).putExtra("id", boothBean.getRes().get(position).getId()));
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


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) etSearch.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    ZhanweiSearchActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            if (etSearch.getText().toString().isEmpty()) {
                Toast.makeText(context, getString(R.string.Enter_Keywords), Toast.LENGTH_SHORT).show();
                return true;
            }
            getdata();
            return true;
        }
        return false;
    }
}

