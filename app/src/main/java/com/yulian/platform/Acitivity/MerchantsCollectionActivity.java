package com.yulian.platform.Acitivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AbsListView;
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
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;
import com.yulian.platform.Adapter.MerchantsCollectionAdapter;
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.DocollBean;
import com.yulian.platform.Bean.ShcollBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.View.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.yulian.platform.R.id.rll_deleteall;

public class MerchantsCollectionActivity extends BaseActivity implements android.view.View.OnClickListener, OnLoadMoreListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.rl_deleteall)
    LinearLayout rlDeleteall;
    @BindView(R.id.img_all)
    ImageView imgAll;
    @BindView(R.id.rl_checkall)
    RelativeLayout rlCheckall;
    @BindView(rll_deleteall)
    RelativeLayout rllDeleteall;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private View foot;
    private MerchantsCollectionAdapter merchantsCollectionAdapter;
    private int page = 1;
    private Dialog dialog;
    private boolean cball = false;
    private Dialog dialog1;
    private int scrolledY = 0;

    @Override
    protected int setthislayout() {
        return R.layout.activity_merchants_collection;
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
        imgBack.setOnClickListener(this);
        rlCheckall.setOnClickListener(this);
        rlDeleteall.setOnClickListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeTarget.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    protected void initData() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "shcoll", new Response.Listener<String>() {
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
                            if (merchantsCollectionAdapter == null) {
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
                        ShcollBean ShcollBean = new Gson().fromJson(decode, ShcollBean.class);
                        if (ShcollBean.getStu().equals("1")) {
                            if (page == 1) {
                                if (swipeTarget != null) {
                                    merchantsCollectionAdapter = new MerchantsCollectionAdapter(MerchantsCollectionActivity.this, (ArrayList) ShcollBean.getRes(), rllDeleteall, llEmpty, swipeTarget);
                                    if (swipeTarget != null)
                                        swipeTarget.setAdapter(merchantsCollectionAdapter);
                                    if (swipeTarget != null)
                                        swipeTarget.setEnabled(true);
                                    if (ShcollBean.getRes().size() < 10) {
                                        if (foot != null) {
                                            foot.setVisibility(View.GONE);
                                            TextView tv_foot_more = (TextView) foot.findViewById(R.id.tv_foot_more);
                                            tv_foot_more.setText(getResources().getString(R.string.NOTMORE));
                                            if (swipeToLoadLayout != null) {
                                                swipeToLoadLayout.setLoadingMore(false);
                                                swipeToLoadLayout.setLoadMoreEnabled(false);
                                            }
                                        }
                                    }

                                }
                            } else {
                                if (merchantsCollectionAdapter != null)
                                    merchantsCollectionAdapter.setDatas((ArrayList) ShcollBean.getRes());
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
                            if (merchantsCollectionAdapter != null) {
                                merchantsCollectionAdapter.notifyDataSetChanged();
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
            }) {
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
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.rl_checkall:
                cball = !cball;
                if (cball) {
                    imgAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.check_true));
                } else {
                    imgAll.setBackgroundDrawable(getResources().getDrawable(R.drawable.check_false));
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
                                    dialog1 = Utils.showLoadingDialog(context);
                                    dialog1.show();
                                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "muti_shcoll", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            dialog1.dismiss();
                                            String decode = Utils.decode(s);
                                            DocollBean docollBean = new Gson().fromJson(decode, DocollBean.class);
                                            if (docollBean.getStu().equals("1")) {
                                                if (context != null)
                                                    Toast.makeText(context, R.string.cancelcollection, Toast.LENGTH_SHORT).show();

                                                if (merchantsCollectionAdapter != null) {
                                                    merchantsCollectionAdapter.getDatas().clear();
                                                    merchantsCollectionAdapter.notifyDataSetChanged();
                                                }

                                                if (llEmpty != null) {
                                                    llEmpty.setVisibility(View.VISIBLE);
                                                }

                                                if (rllDeleteall != null) {
                                                    rllDeleteall.setVisibility(View.GONE);
                                                }

                                                if (swipeTarget != null) {
                                                    swipeTarget.setVisibility(View.GONE);
                                                    swipeToLoadLayout.setEnabled(false);
                                                }

                                                new Thread() {
                                                    @Override
                                                    public void run() {
                                                        super.run();
                                                        PushAgent.getInstance(context).getTagManager().reset(new TagManager.TCallBack() {
                                                            @Override
                                                            public void onMessage(boolean isSuccess, ITagManager.Result result) {

                                                            }
                                                        });
                                                    }
                                                }.start();
                                            } else {
                                                if (context != null)
                                                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            dialog1.dismiss();
                                            volleyError.printStackTrace();
                                            if (context != null)
                                                EasyToast.showShort(context, getString(R.string.Networkexception));
                                        }
                                    }) {
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
