package com.zzcn77.android_app_company.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.zzcn77.android_app_company.Adapter.GVProuctAdapter;
import com.zzcn77.android_app_company.Adapter.ProductAdapter;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;
import com.zzcn77.android_app_company.View.MyGridView;
import com.zzcn77.android_app_company.View.PowersearchDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class ProductFragment extends BaseFragment implements OnLoadMoreListener, View.OnClickListener {
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
    private ProductAdapter adapter;

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_product_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        ArrayList<Object> dates = new ArrayList<>();
        dates.add("");
        dates.add("");
        dates.add("");
        dates.add("");
        dates.add("");
        dates.add("");
        dates.add("");
        swipeToLoadLayout.setOnLoadMoreListener(this);
        View head = View.inflate(mActivity, R.layout.product_head_layout, null);
        gvSwipeTarget = (MyGridView) head.findViewById(R.id.gv_swipe_target);
        swipeTarget.addHeaderView(head);
        adapter = new ProductAdapter(mActivity, dates);
        swipeTarget.setAdapter(adapter);
        gvSwipeTarget.setAdapter(new GVProuctAdapter(mActivity, dates));
        imgPowerSearch.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);

    }

    //上拉加载
    @Override
    public void onLoadMore() {
        swipeTarget.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList datas = adapter.getDatas();
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
                swipeToLoadLayout.setLoadingMore(false);
                swipeTarget.setEnabled(true);
                EasyToast.showShort(mActivity, "加载完成");
            }
        }, 2000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_power_search:
                new PowersearchDialog.Builder(mActivity).create().show();
                break;

        }
    }
}
