package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.zzcn77.android_app_company.Adapter.CollectAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.CollBean;
import com.zzcn77.android_app_company.Bean.DocollBean;
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

import static com.zzcn77.android_app_company.R.id.rll_deleteall;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class MyCollectActivity extends BaseActivity implements View.OnClickListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_all)
    ImageView imgall;
    @BindView(R.id.rl_checkall)
    RelativeLayout rlCheckall;
    @BindView(R.id.rl_deleteall)
    LinearLayout rlDeleteall;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(rll_deleteall)
    RelativeLayout rllDeleteall;
    private CollectAdapter collectAdapter;
    private int page = 1;
    private Dialog dialog;
    private boolean cball = false;
    private int scrolledY = 0;

    @Override
    protected int setthislayout() {
        return R.layout.mycollect_layout;
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
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeTarget.setOnItemClickListener(this);
        rlCheckall.setOnClickListener(this);
        rlDeleteall.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        swipeTarget.setOnScrollListener(new AbsListView.OnScrollListener()

        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                scrolledY = view.getFirstVisiblePosition();
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
    }

    @Override
    protected void initview() {
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
    }


    @Override
    protected void initData() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "coll", new Response.Listener<String>() {

                @Override
                public void onResponse(String s) {
                    String decode = Utils.decode(s);
                    if (decode.isEmpty()) {
                        if (swipeToLoadLayout != null)
                            swipeToLoadLayout.setLoadingMore(false);
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Networkexception));
                    } else {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (decode.contains("code\":\"111\"")) {
                            if (page == 1) {
                                if (swipeTarget != null)
                                    swipeTarget.setVisibility(View.GONE);
                                if (rllDeleteall != null)
                                    rllDeleteall.setVisibility(View.GONE);
                                if (llEmpty != null)
                                    llEmpty.setVisibility(View.VISIBLE);
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadMoreEnabled(false);
                                return;
                            }
                            page = page - 1;
                            if (swipeToLoadLayout != null)
                                swipeToLoadLayout.setLoadingMore(false);
                            if (swipeTarget != null)
                                swipeTarget.setEnabled(true);
                            if (collectAdapter == null) {
                                if (llEmpty != null)
                                    llEmpty.setVisibility(View.VISIBLE);
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadMoreEnabled(false);
                                return;
                            }
                            if (context != null)
                                Toast.makeText(context, getString(R.string.NOTMORE), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (rllDeleteall != null)
                            rllDeleteall.setVisibility(View.VISIBLE);
                        CollBean collBean = new Gson().fromJson(decode, CollBean.class);
                        if (collBean.getStu().equals("1")) {
                            if (page == 1) {
                                if (swipeTarget != null) {
                                    if (collectAdapter != null)
                                        collectAdapter = new CollectAdapter(context, (ArrayList) collBean.getRes(), rllDeleteall, llEmpty);
                                    if (swipeTarget != null)
                                        swipeTarget.setAdapter(collectAdapter);
                                    if (swipeTarget != null)
                                        swipeTarget.setEnabled(true);
                                }
                            } else {
                                if (collectAdapter != null)
                                    collectAdapter.setDatas((ArrayList) collBean.getRes());
                                if (swipeToLoadLayout != null)
                                    swipeToLoadLayout.setLoadingMore(false);
                                if (swipeTarget != null)
                                    swipeTarget.setEnabled(true);
                            }
                            if (swipeTarget != null) {
                                swipeTarget.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeTarget.setSelection(scrolledY);
                                    }
                                });
                            }

                            if (collectAdapter != null) {
                                collectAdapter.notifyDataSetChanged();
                            }
                        } else {
                            if (swipeToLoadLayout != null)
                                swipeToLoadLayout.setLoadingMore(false);
                            if (context != null)
                                EasyToast.showShort(context, getString(R.string.Abnormalserver));
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                    if (swipeToLoadLayout != null)
                        swipeToLoadLayout.setLoadingMore(false);
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Networkexception));
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("key", UrlUtils.key);
                    map.put("p", String.valueOf(page));
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
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Notconnect));
            }
        } catch (Exception e) {
            // 可忽略的异常
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2017/6/5
        page = 1;
        initData();
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        if (swipeTarget != null)
            swipeTarget.setEnabled(false);
        if (swipeToLoadLayout != null)
            swipeToLoadLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    page = page + 1;
                    initData();
                }
            }, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_checkall:
                cball = !cball;
                if (cball) {
                    imgall.setBackground(getResources().getDrawable(R.drawable.check_true));
                } else {
                    imgall.setBackground(getResources().getDrawable(R.drawable.check_false));
                }
                break;
            case R.id.rl_deleteall:
                if (cball) {
                    new AlertDialog.Builder(context).setTitle(R.string.message)//设置对话框标题
                            .setMessage(R.string.deleteall)//设置显示的内容
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();

                                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "muti_coll", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            String decode = Utils.decode(s);
                                            DocollBean docollBean = new Gson().fromJson(decode, DocollBean.class);
                                            if (docollBean.getStu().equals("1")) {
                                                if (context != null)
                                                    Toast.makeText(context, R.string.cancelcollection, Toast.LENGTH_SHORT).show();
                                                if (swipeTarget != null)
                                                    swipeTarget.setAdapter(new CollectAdapter(context, new ArrayList(), rllDeleteall, llEmpty));
                                                if (llEmpty != null)
                                                    llEmpty.setVisibility(View.VISIBLE);
                                                if (rllDeleteall != null)
                                                    rllDeleteall.setVisibility(View.GONE);
                                            } else {
                                                if (context != null)
                                                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            volleyError.printStackTrace();
                                            if (context != null)
                                                EasyToast.showShort(context, getString(R.string.Networkexception));
                                        }
                                    })

                                    {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> map = new HashMap<String, String>();
                                            map.put("key", UrlUtils.key);
                                            map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                                            return map;
                                        }
                                    };
                                    boolean connected = Utils.isConnected(context);
                                    if (connected) {
                                        requestQueue.add(stringRequest);
                                    } else {
                                        if (context != null)
                                            EasyToast.showShort(context, getString(R.string.Notconnect));
                                    }
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框


                }
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = collectAdapter.getItem(position);
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("id", item);
        startActivity(intent);
    }

}
