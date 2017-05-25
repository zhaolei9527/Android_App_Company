package com.zzcn77.android_app_company.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.hintview.IconHintView;
import com.zzcn77.android_app_company.Acitivity.CompanyDetailsActivity;
import com.zzcn77.android_app_company.Acitivity.NewsActivity;
import com.zzcn77.android_app_company.Acitivity.NewsDetailsActivity;
import com.zzcn77.android_app_company.Acitivity.PeomotionDetailsActivity;
import com.zzcn77.android_app_company.Acitivity.PromotionActivity;
import com.zzcn77.android_app_company.Adapter.LoopAdapter;
import com.zzcn77.android_app_company.Adapter.Promotionadapter;
import com.zzcn77.android_app_company.Bean.IndexBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.CallPhoneUtils;
import com.zzcn77.android_app_company.Utils.DensityUtils;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.View.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.zzcn77.android_app_company.R.id.ll_callphone;

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
    MyListView lvPromotion;
    @BindView(R.id.tv_morePromtion)
    TextView tvMorePromtion;
    @BindView(R.id.rl_title_Company_Details)
    RelativeLayout rlTitleCompanyDetails;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.rl_title_news_more)
    RelativeLayout rlTitleNewsMore;
    @BindView(R.id.rl_title_promotion_more)
    RelativeLayout rlTitlePromotionMore;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_callphone)
    LinearLayout llCallphone;
    @BindView(R.id.sv)
    ScrollView sv;

    //最新动态列表
    class newsAdapter extends PagerAdapter {

        ArrayList<IndexBean.ResBean.DongtaiBean> datas = new ArrayList<>();

        public newsAdapter(List datas) {
            this.datas = (ArrayList<IndexBean.ResBean.DongtaiBean>) datas;
        }

        @Override
        public int getCount() {
            return datas.size();
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
        public Object instantiateItem(ViewGroup container, final int position) {

            View inflate = View.inflate(mActivity, R.layout.news_item_layout, null);
            com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView = (com.facebook.drawee.view.SimpleDraweeView) inflate.findViewById(R.id.SimpleDraweeView);
            TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
            TextView tvmessage = (TextView) inflate.findViewById(R.id.tv_message);
            tv_title.setText(datas.get(position).getTitle());
            tvmessage.setText(datas.get(position).getKeywords());
            SimpleDraweeView.setImageURI(UrlUtils.BaseImg + datas.get(position).getImgurl());

            container.addView(inflate);

            inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, NewsDetailsActivity.class));
                }
            });
            return inflate;
        }
    }


    //最新动态
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            tvNewItem.setText(String.valueOf(position + 1) + "/" + vpNews.getAdapter().getCount());

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.rl_Company_Details:
            case R.id.rl_title_Company_Details:
                startActivity(new Intent(mActivity, CompanyDetailsActivity.class));
                break;
            case R.id.rl_title_promotion_more:
                startActivity(new Intent(mActivity, PromotionActivity.class));
                break;
            case R.id.rl_title_news_more:
                startActivity(new Intent(mActivity, NewsActivity.class));
                break;
            case ll_callphone:
                CallPhoneUtils.CallPhone(mActivity, tvPhone.getText().toString());
                break;

        }

    }


    @Override
    protected int setLayoutResouceId() {
        return R.layout.f_home_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        RollPagerView.setHintView(new IconHintView(mActivity, R.drawable.shape_selected, R.drawable.shape_noraml, DensityUtils.dp2px(mActivity, getResources().getDimension(R.dimen.x7))));
        RollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EasyToast.showShort(mActivity, "position" + position);
            }
        });
        RollPagerView.setPlayDelay(3000);
        Utils.displayImageFresco(R.drawable.tu, SimpleDraweeView);
        //假数据
        vpNews.addOnPageChangeListener(onPageChangeListener);
        llCallphone.setOnClickListener(this);
        rlCompanyDetails.setOnClickListener(this);
        rlTitleCompanyDetails.setOnClickListener(this);
        rlTitleNewsMore.setOnClickListener(this);
        rlTitlePromotionMore.setOnClickListener(this);
        lvPromotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mActivity, PeomotionDetailsActivity.class));
            }
        });

    }

    private void getindex() {
        IndexBean index = new Gson().fromJson(SPUtil.get(mActivity, "index", "").toString(), IndexBean.class);
        lvPromotion.setAdapter(new Promotionadapter(mActivity, index.getRes().getHuodong()));
        RollPagerView.setAdapter(new LoopAdapter(RollPagerView, index.getRes().getLunbo()));
        tvTitle.setText(index.getRes().getJianjie().getTitle());
        SimpleDraweeView.setImageURI(UrlUtils.BaseImg + index.getRes().getJianjie().getPic());
        tvMessage.setText(index.getRes().getJianjie().getKeywords());
        tvPhone.setText(index.getRes().getJianjie().getTel());
        vpNews.setAdapter(new newsAdapter(index.getRes().getDongtai()));
        tvNewItem.setText(String.valueOf(1) + "/" + vpNews.getAdapter().getCount());
        sv.scrollTo(0, 0);

    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        getindex();
    }
}
