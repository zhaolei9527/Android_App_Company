package com.yulian.platform.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.yulian.platform.Bean.IndexBean;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.VolleyLoadPicture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵磊 on 2017/5/25.
 */
//轮播图
public class LoopAdapter extends LoopPagerAdapter {
    //

    private ArrayList<IndexBean.ResBean.LunboBean> imgs = new ArrayList<>();

    public LoopAdapter(RollPagerView viewPager, List imgs) {
        super(viewPager);
        this.imgs = (ArrayList) imgs;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        VolleyLoadPicture vlp = new VolleyLoadPicture(container.getContext(), view);
        vlp.getmImageLoader().get(UrlUtils.BaseImg + imgs.get(position).getImg(), vlp.getOne_listener());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return imgs.size();
    }
}