package com.zzcn77.android_app_company.Acitivity;

import android.graphics.Color;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.zzcn77.android_app_company.Adapter.Promotionadapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by 赵磊 on 2017/5/19.
 */

public class PromotionActivity extends BaseActivity implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {

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
    private Promotionadapter promotionadapter;

    @Override
    protected int setthislayout() {
        return R.layout.promotion_layout;
    }

    @Override
    protected void initview() {
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
    }

    @Override
    protected void initListener() {
        swipeToLoadLayout.setOnLoadMoreListener(this);
        SwipeRefreshLayout.setOnRefreshListener(this);
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
    }

    @Override
    protected void initData() {
        //假数据
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        promotionadapter = new Promotionadapter(context, arrayList);
        lvSwipeTarget.setAdapter(promotionadapter);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        lvSwipeTarget.setEnabled(false);
        SwipeRefreshLayout.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList datas = promotionadapter.getDatas();
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                promotionadapter.setDatas(datas);
                promotionadapter.notifyDataSetChanged();
                swipeToLoadLayout.setLoadingMore(false);
                lvSwipeTarget.setEnabled(true);
                SwipeRefreshLayout.setEnabled(true);
                EasyToast.showShort(context, "加载完成");
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
                ArrayList arrayList = new ArrayList();
                arrayList.add("");
                arrayList.add("");
                arrayList.add("");
                arrayList.add("");
                arrayList.add("");
                arrayList.add("");
                promotionadapter.setDatas(arrayList);
                promotionadapter.notifyDataSetChanged();
                lvSwipeTarget.setEnabled(true);
                SwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}