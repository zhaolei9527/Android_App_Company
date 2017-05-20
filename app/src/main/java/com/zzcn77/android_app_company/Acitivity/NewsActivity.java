package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.zzcn77.android_app_company.Adapter.Newsadapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by 赵磊 on 2017/5/19.
 */

public class NewsActivity extends BaseActivity implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemClickListener {

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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Newsadapter newsadapter;

    @Override
    protected int setthislayout() {
        return R.layout.promotion_layout;
    }

    @Override
    protected void initview() {
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
        tvTitle.setText(R.string.recentnews);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
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

        lvSwipeTarget.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        //假数据
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        newsadapter = new Newsadapter(context, arrayList);
        lvSwipeTarget.setAdapter(newsadapter);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        lvSwipeTarget.setEnabled(false);
        SwipeRefreshLayout.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList datas = newsadapter.getDatas();
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                newsadapter.setDatas(datas);
                newsadapter.notifyDataSetChanged();
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
                newsadapter.setDatas(arrayList);
                newsadapter.notifyDataSetChanged();
                lvSwipeTarget.setEnabled(true);
                SwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }

    }

    //条目点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(context, NewsDetailsActivity.class));

    }
}