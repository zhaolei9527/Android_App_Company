package com.zzcn77.android_app_company.Acitivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.zzcn77.android_app_company.Adapter.CollectAdapter;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.View.LoadMoreFooterView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class MyCollectActivity extends BaseActivity implements View.OnClickListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.rl_checkall)
    RelativeLayout rlCheckall;
    @BindView(R.id.rl_deleteall)
    RelativeLayout rlDeleteall;
    @BindView(R.id.swipe_target)
    ListView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private CollectAdapter collectAdapter;

    @Override
    protected int setthislayout() {
        return R.layout.mycollect_layout;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeTarget.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        //假数据
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        collectAdapter = new CollectAdapter(context, arrayList);
        swipeTarget.setAdapter(collectAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }
    }

    @Override
    public void onLoadMore() {
        swipeTarget.setEnabled(false);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList datas = collectAdapter.getDatas();
                datas.add("");
                datas.add("");
                datas.add("");
                datas.add("");
                collectAdapter.setDatas(datas);
                collectAdapter.notifyDataSetChanged();
                swipeToLoadLayout.setLoadingMore(false);
                swipeTarget.setEnabled(true);
                EasyToast.showShort(context, "加载完成");
            }
        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
