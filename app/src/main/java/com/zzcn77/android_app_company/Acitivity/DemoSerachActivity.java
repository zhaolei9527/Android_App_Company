package com.zzcn77.android_app_company.Acitivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class DemoSerachActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.swipe_target)
    GridView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.SwipeRefreshLayout)
    android.support.v4.widget.SwipeRefreshLayout SwipeRefreshLayout;

    @Override
    protected int setthislayout() {
        return R.layout.demo_search_layout;
    }

    @Override
    protected void initview() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
