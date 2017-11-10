package com.yulian.platform.Adapter;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.yulian.platform.Bean.IndexBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.UrlUtils;

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
        View inflate = View.inflate(container.getContext(), R.layout.layout_img, null);
        SimpleDraweeView SimpleDraweeView = (com.facebook.drawee.view.SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
        SimpleDraweeView.setImageURI(UrlUtils.BaseImg + imgs.get(position).getImg());
        return inflate;
    }

    @Override
    public int getRealCount() {
        return imgs.size();
    }
}