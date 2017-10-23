package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.zzcn77.android_app_company.Adapter.ExhibitionAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.DescBean;
import com.zzcn77.android_app_company.Bean.ExhibitionBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;
import com.zzcn77.android_app_company.Volley.VolleyInterface;
import com.zzcn77.android_app_company.Volley.VolleyRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class TheExhibitionHallActivity extends BaseActivity implements android.view.View.OnClickListener, AdapterView.OnItemClickListener, OnLoadMoreListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
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
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search_zhanwei)
    LinearLayout llSearchZhanwei;
    private View foot;
    private ExhibitionAdapter exhibitionAdapter;
    private int page = 1;
    private Dialog dialog;
    private ExhibitionBean exhibitionBean;
    private LinearLayout head;
    TextView tvContent;
    Button btnSjtuijian;
    @Override
    protected int setthislayout() {
        return R.layout.activity_the_exhibition_hall;
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        foot = View.inflate(context, R.layout.list_foot_layout, null);
        swipeTarget.addFooterView(foot, null, false);
        head = (LinearLayout) View.inflate(context, R.layout.exhibition_homepage_head_layout, null);
        tvContent = (TextView) head.findViewById(R.id.tv_content);
        btnSjtuijian = (Button) head.findViewById(R.id.btn_sjtuijian);
        btnSjtuijian.setOnClickListener(this);
        swipeTarget.addHeaderView(head);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        llSearchZhanwei.setOnClickListener(this);
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

        HashMap<String, String> params = new HashMap<>();
        params.put("key", UrlUtils.key);
        VolleyRequest.RequestPost(context, UrlUtils.BaseUrl22 + "desc", "desc", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = Utils.decode(result);
                Log.d("TheEntranceActivity", decode);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    DescBean descBean = new Gson().fromJson(decode, DescBean.class);
                    tvContent.setText(descBean.getRes().getContent().toString());
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
            }
        });


    }

    @Override
    protected void initData() {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl22 + "exhibition", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (dialog != null)
                    dialog.dismiss();
                String decode = Utils.decode(s);
                Log.d("TheExhibitionHallActivi", decode);
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
                    exhibitionBean = new Gson().fromJson(decode, ExhibitionBean.class);
                    if (exhibitionBean.getStu().equals("1")) {
                        if (exhibitionBean.getRes().size() < 10) {
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
                            exhibitionAdapter = new ExhibitionAdapter(context, (ArrayList) exhibitionBean.getRes());
                            if (swipeTarget != null)
                                swipeTarget.setAdapter(exhibitionAdapter);
                        } else {
                            if (exhibitionAdapter != null)
                                exhibitionAdapter.setDatas((ArrayList) exhibitionBean.getRes());
                        }
                        if (exhibitionAdapter != null)
                            exhibitionAdapter.notifyDataSetChanged();

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

                if (dialog != null)
                    dialog.dismiss();

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
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_search_zhanwei:
                startActivity(new Intent(context, ZhanweiSearchActivity.class));
                break;
            case R.id.btn_sjtuijian:
                startActivity(new Intent(context, BusinessmenRecommendActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(context, BigImageActivity.class).putExtra("imgurl", UrlUtils.BaseImg + exhibitionBean.getRes().get(position).getImgurl()));
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
