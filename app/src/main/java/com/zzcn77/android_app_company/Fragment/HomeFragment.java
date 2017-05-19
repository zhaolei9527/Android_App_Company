package com.zzcn77.android_app_company.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.zzcn77.android_app_company.Acitivity.CompanyDetailsActivity;
import com.zzcn77.android_app_company.Acitivity.PromotionActivity;
import com.zzcn77.android_app_company.Adapter.Promotionadapter;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.DensityUtils;
import com.zzcn77.android_app_company.Utils.EasyToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class HomeFragment extends BaseFragment implements android.view.View.OnClickListener {


    @BindView(R.id.RollPagerView)
    com.jude.rollviewpager.RollPagerView RollPagerView;
    @BindView(R.id.View)
    android.view.View View;
    @BindView(R.id.img_Company_Details)
    ImageView imgCompanyDetails;
    @BindView(R.id.tv_Company_Details)
    TextView tvCompanyDetails;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.ll_callphone)
    LinearLayout llCallphone;
    @BindView(R.id.rl_Company_Details)
    RelativeLayout rlCompanyDetails;
    @BindView(R.id.View2)
    android.view.View View2;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.vp_news)
    ViewPager vpNews;
    @BindView(R.id.tv_new_item)
    TextView tvNewItem;
    @BindView(R.id.View3)
    android.view.View View3;
    @BindView(R.id.img_morePromtion)
    ImageView img_morePromtion;
    @BindView(R.id.lv_Promotion)
    ListView lvPromotion;
    @BindView(R.id.tv_morePromtion)
    TextView tvMorePromtion;
    Unbinder unbinder;
    //最新动态列表
    private PagerAdapter newsAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            }
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View inflate = View.inflate(mActivity, R.layout.news_item_layout, null);
            com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView = (com.facebook.drawee.view.SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
            SimpleDraweeView.setImageURI(Uri.parse("http://img12.360buyimg.com/n1/s180x180_jfs/t2860/4/1183024733/452142/9149c9e7/5736c828N85887f78.png"));
            container.addView(inflate);

            return inflate;
        }
    };
    //最新动态
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            tvNewItem.setText(String.valueOf(position + 1) + "/10");

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.img_Company_Details:
            case R.id.tv_Company_Details:
            case R.id.rl_Company_Details:
                startActivity(new Intent(mActivity, CompanyDetailsActivity.class));
                break;

            case R.id.img_morePromtion:
            case R.id.tv_morePromtion:
                startActivity(new Intent(mActivity, PromotionActivity.class));
                break;

        }

    }

    //轮播图
    private class LoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.drawable.i_banner,
                R.drawable.i_banner,
                R.drawable.i_banner,
                R.drawable.i_banner,
        };

        public LoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }


    //测量ListView高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_home_layout;
    }

    @Override
    protected void initView() {
        super.initView();


        RollPagerView.setHintView(new IconHintView(mActivity, R.drawable.shape_selected, R.drawable.shape_noraml, DensityUtils.dp2px(mActivity, 20)));
        RollPagerView.setAdapter(new LoopAdapter(RollPagerView));
        RollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EasyToast.showShort(mActivity, "position" + position);
            }
        });
        RollPagerView.setPlayDelay(3000);
        lvPromotion.post(new Runnable() {
            @Override
            public void run() {
                setListViewHeightBasedOnChildren(lvPromotion);
            }
        });
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("http://img12.360buyimg.com/n1/s180x180_jfs/t2860/4/1183024733/452142/9149c9e7/5736c828N85887f78.png"))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(SimpleDraweeView.getController())
                .build();
        SimpleDraweeView.setController(controller);

        //假数据
        ArrayList arrayList = new ArrayList();
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        lvPromotion.setAdapter(new Promotionadapter(mActivity, arrayList));

        vpNews.setAdapter(newsAdapter);


        vpNews.addOnPageChangeListener(onPageChangeListener);

        imgCompanyDetails.setOnClickListener(this);
        tvCompanyDetails.setOnClickListener(this);
        rlCompanyDetails.setOnClickListener(this);
        img_morePromtion.setOnClickListener(this);
        tvMorePromtion.setOnClickListener(this);
    }


}
