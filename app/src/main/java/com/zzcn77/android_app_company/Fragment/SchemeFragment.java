package com.zzcn77.android_app_company.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.zzcn77.android_app_company.Acitivity.SchemeAcitivty;
import com.zzcn77.android_app_company.Adapter.SchemeAdapter;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class SchemeFragment extends BaseFragment implements OnLoadMoreListener, android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;
    private SchemeAdapter schemeAdapter;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_scheme_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        //改变加载显示的颜色
        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        SwipeRefreshLayout.setOnRefreshListener(this);

        swipeTarget.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mActivity, SchemeAcitivty.class));
            }
        });
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
                SwipeRefreshLayout.setEnabled(enable);
            }
        });
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        //假数据
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        schemeAdapter = new SchemeAdapter(mActivity, arrayList);
        swipeTarget.setAdapter(schemeAdapter);
    }

    //上拉加载
    @Override
    public void onLoadMore() {
        swipeTarget.setEnabled(false);
        SwipeRefreshLayout.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList datas = schemeAdapter.getDatas();
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                schemeAdapter.setDatas(datas);
                schemeAdapter.notifyDataSetChanged();
                swipeToLoadLayout.setLoadingMore(false);
                swipeTarget.setEnabled(true);
                SwipeRefreshLayout.setEnabled(true);
                EasyToast.showShort(mActivity, "加载完成");
            }
        }, 2000);

    }

    //下拉刷新

    @Override
    public void onRefresh() {
        swipeTarget.setEnabled(false);

        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList datas = schemeAdapter.getDatas();
                datas.clear();
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                schemeAdapter.setDatas(datas);
                schemeAdapter.notifyDataSetChanged();

                swipeTarget.setEnabled(true);
                SwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }


}
