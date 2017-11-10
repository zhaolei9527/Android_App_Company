package com.yulian.platform.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.hintview.IconHintView;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;
import com.yulian.platform.Acitivity.ChatActivity;
import com.yulian.platform.Acitivity.CompanyDetailsActivity;
import com.yulian.platform.Acitivity.LoginActivity;
import com.yulian.platform.Acitivity.NewsActivity;
import com.yulian.platform.Acitivity.NewsDetailsActivity;
import com.yulian.platform.Acitivity.PeomotionDetailsActivity;
import com.yulian.platform.Acitivity.ProductDetailsActivity;
import com.yulian.platform.Acitivity.PromotionActivity;
import com.yulian.platform.Acitivity.TheEntranceActivity;
import com.yulian.platform.Adapter.LoopAdapter;
import com.yulian.platform.Adapter.Promotionadapter;
import com.yulian.platform.App;
import com.yulian.platform.Bean.IndexBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.CallPhoneUtils;
import com.yulian.platform.Utils.DensityUtils;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.Other;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.View.MyDialog;
import com.yulian.platform.View.MyListView;
import com.yulian.platform.Volley.VolleyInterface;
import com.yulian.platform.Volley.VolleyRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

import static anet.channel.util.Utils.context;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class HomeFragment extends BaseFragment implements android.view.View.OnClickListener {

    @BindView(R.id.RollPagerView)
    com.jude.rollviewpager.RollPagerView RollPagerView;
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
    @BindView(R.id.View3)
    android.view.View View3;
    @BindView(R.id.img_morePromtion)
    ImageView img_morePromtion;
    @BindView(R.id.lv_Promotion)
    MyListView lvPromotion;
    @BindView(R.id.tv_morePromtion)
    TextView tvMorePromtion;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.rl_title_news_more)
    RelativeLayout rlTitleNewsMore;
    @BindView(R.id.rl_title_promotion_more)
    RelativeLayout rlTitlePromotionMore;
    @BindView(R.id.img_Company_CallPhone)
    ImageView img_Company_CallPhone;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.Xcircleindicator)
    com.yulian.platform.View.Xcircleindicator Xcircleindicator;
    Unbinder unbinder;
    @BindView(R.id.img_home_logo)
    com.facebook.drawee.view.SimpleDraweeView imgHomeLogo;
    @BindView(R.id.btn_changeCompany)
    Button btnChangeCompany;
    @BindView(R.id.img_shoucang)
    ImageView imgShoucang;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.View)
    android.view.View View;
    @BindView(R.id.img_Company_IM)
    ImageView imgCompanyIM;
    @BindView(R.id.rl_title_Company_Details)
    RelativeLayout rlTitleCompanyDetails;
    @BindView(R.id.tv_pingfen)
    TextView tvPingfen;
    @BindView(R.id.tv_id)
    TextView tv_id;
    private Promotionadapter promotionadapter;
    private IndexBean index;
    private Dialog dialog;
    private int is_coll;

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        App.queues.cancelAll("do_shcoll");
        super.onDestroy();
    }

    //最新动态列表asdasd
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
                    Intent intent = new Intent(mActivity, NewsDetailsActivity.class);
                    intent.putExtra("id", String.valueOf(datas.get(position).getId()));
                    mActivity.startActivity(intent);
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
            Xcircleindicator.setCurrentPage(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

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
                if (Other.NEWSDETAILSACTIVITY.equals(index.getRes().getLunbo().get(position).getType())) {
                    Intent intent = new Intent(mActivity, NewsDetailsActivity.class);
                    intent.putExtra("id", index.getRes().getLunbo().get(position).getBid());
                    startActivity(intent);
                } else if (Other.PEOMOTIONDETAILSACTIVITY.equals(index.getRes().getLunbo().get(position).getType())) {
                    Intent intent = new Intent(mActivity, PeomotionDetailsActivity.class);
                    intent.putExtra("id", index.getRes().getLunbo().get(position).getBid());
                    startActivity(intent);
                } else if (Other.PRODUCTDETAILSACTIVITY.equals(index.getRes().getLunbo().get(position).getType()) || Other.PRODUCTSTARDETAILSACTIVITY.equals(index.getRes().getLunbo().get(position).getType())) {
                    Intent intent = new Intent(mActivity, ProductDetailsActivity.class);
                    intent.putExtra("id", index.getRes().getLunbo().get(position).getBid());
                    startActivity(intent);
                } else {

                }
            }
        });
        RollPagerView.setPlayDelay(3000);
        //假数据
        vpNews.addOnPageChangeListener(onPageChangeListener);
        img_Company_CallPhone.setOnClickListener(this);
        rlCompanyDetails.setOnClickListener(this);
        rlTitleNewsMore.setOnClickListener(this);
        rlTitlePromotionMore.setOnClickListener(this);
        btnChangeCompany.setOnClickListener(this);
        imgShoucang.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgCompanyIM.setOnClickListener(this);

        lvPromotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private Intent intent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(mActivity, PeomotionDetailsActivity.class);
                intent.putExtra("id", promotionadapter.getItem(position));
                startActivity(intent);
            }
        });

    }

    private void getindex() {
        index = new Gson().fromJson(SPUtil.get(mActivity, "index", "").toString(), IndexBean.class);
        promotionadapter = new Promotionadapter(mActivity, index.getRes().getHuodong());
        lvPromotion.setAdapter(promotionadapter);
        RollPagerView.setAdapter(new LoopAdapter(RollPagerView, index.getRes().getLunbo()));
        tvTitle.setText(index.getRes().getJianjie().getTitle());
        SimpleDraweeView.setImageURI(UrlUtils.BaseImg + index.getRes().getJianjie().getImgurl());
        tv_id.setText(getString(R.string.The_company_code) + ":" + String.valueOf(index.getRes().getJianjie().getCodes()));
        tvPingfen.setText(String.valueOf(index.getRes().getJianjie().getPoint()));
        tvMessage.setText(index.getRes().getJianjie().getKeywords());
        vpNews.setAdapter(new newsAdapter(index.getRes().getDongtai()));
        //设置总共的页数
        Xcircleindicator.initData(index.getRes().getDongtai().size(), 0);
        //设置当前的页面
        Xcircleindicator.setCurrentPage(0);
        Xcircleindicator.setFillColor(getResources().getColor(R.color.text_blue));
        Xcircleindicator.setStrokeColor(getResources().getColor(R.color.text_check));
        Xcircleindicator.setRadius(15);
        sv.scrollTo(0, 0);
    }

    @Override
    protected void initData(Bundle arguments) {
        super.initData(arguments);
        getindex();
        is_coll = index.getRes().getIs_coll();
        if (is_coll == 1) {
            imgShoucang.setBackground(getResources().getDrawable(R.mipmap.sc2));
        } else {
            imgShoucang.setBackground(getResources().getDrawable(R.mipmap.sc));
        }
        imgHomeLogo.setImageURI(UrlUtils.BaseImg + index.getRes().getJianjie().getLogo());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_Company_Details:
                startActivity(new Intent(mActivity, CompanyDetailsActivity.class));
                break;
            case R.id.rl_title_promotion_more:
                startActivity(new Intent(mActivity, PromotionActivity.class));
                break;
            case R.id.rl_title_news_more:
                startActivity(new Intent(mActivity, NewsActivity.class));
                break;
            case R.id.img_Company_CallPhone:
                CallPhoneUtils.CallPhone(mActivity, index.getRes().getJianjie().getTel());
                break;
            case R.id.btn_changeCompany:
                mActivity.startActivity(new Intent(mActivity, TheEntranceActivity.class));
                mActivity.finish();
                break;
            case R.id.img_share:
                showShareDialog();
                break;
            case R.id.img_shoucang:
                shoucangSJ();
                break;
            case R.id.img_Company_IM:
                if (String.valueOf(SPUtil.get(mActivity, "account", "")).isEmpty()) {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                    getActivity().finish();
                } else {
                    SPUtil.putAndApply(getActivity(), String.valueOf(index.getRes().getJianjie().getId()), String.valueOf(index.getRes().getJianjie().getTitle()));
                    startActivity(new Intent(mActivity, ChatActivity.class).putExtra("userId", String.valueOf(index.getRes().getJianjie().getId())));
                }
                break;
        }
    }

    private void shoucangSJ() {
        is_coll = index.getRes().getIs_coll();
        dialog = Utils.showLoadingDialog(mActivity);
        this.dialog.show();
        String id = (String) SPUtil.get(mActivity, "id", "");
        if (id.trim().isEmpty()) {
            Toast.makeText(mActivity, getString(R.string.Youarenotcurrentlyloggedin), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mActivity, LoginActivity.class));
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("key", UrlUtils.key);
            params.put("sh_id", String.valueOf(index.getRes().getJianjie().getId()));
            params.put("uid", id);
            if (is_coll == 1) {
                params.put("stu", "2");
            } else {
                params.put("stu", "1");
            }
            VolleyRequest.RequestPost(context, UrlUtils.BaseUrl21 + "do_shcoll", "do_shcoll", params, new VolleyInterface(context) {
                @Override
                public void onMySuccess(String result) {
                    dialog.dismiss();
                    String decode = Utils.decode(result);
                    Log.d("TheEntranceActivity", decode);
                    if (decode.isEmpty()) {
                        EasyToast.showShort(mActivity, getString(R.string.Networkexception));
                    } else {
                        if (decode.contains("121")) {
                            HomeFragment.this.dialog.dismiss();
                            Toast.makeText(mActivity, getString(R.string.Collection_or_cancel_this_failure), Toast.LENGTH_SHORT).show();
                        } else if (decode.contains("120")) {
                            Toast.makeText(mActivity, getString(R.string.Merchants_have_to_collect), Toast.LENGTH_SHORT).show();
                            index.getRes().setIs_coll(1);
                        } else if (decode.contains("123")) {
                            Toast.makeText(mActivity, getString(R.string.The_member_information_does_not_exist), Toast.LENGTH_SHORT).show();
                        } else if (decode.contains("124")) {
                            Toast.makeText(mActivity, getString(R.string.Merchants_information_does_not_exist), Toast.LENGTH_SHORT).show();
                        } else {
                            if (is_coll == 1) {
                                Toast.makeText(mActivity, getString(R.string.cancelcollection), Toast.LENGTH_SHORT).show();
                                imgShoucang.setBackground(getResources().getDrawable(R.mipmap.sc));
                                index.getRes().setIs_coll(0);
                                SPUtil.putAndApply(mActivity, "index", new Gson().toJson(index));

                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        PushAgent.getInstance(mActivity).getTagManager().delete(new TagManager.TCallBack() {
                                            @Override
                                            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

                                            }
                                        }, String.valueOf(index.getRes().getJianjie().getId()));

                                    }
                                }.start();

                            } else {
                                Toast.makeText(mActivity, getString(R.string.Merchants_have_to_collect), Toast.LENGTH_SHORT).show();
                                imgShoucang.setBackground(getResources().getDrawable(R.mipmap.sc2));
                                index.getRes().setIs_coll(1);
                                SPUtil.putAndApply(mActivity, "index", new Gson().toJson(index));

                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        PushAgent.getInstance(mActivity).getTagManager().add(new TagManager.TCallBack() {
                                            @Override
                                            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {

                                            }
                                        }, String.valueOf(index.getRes().getJianjie().getId()));
                                    }
                                }.start();

                            }
                        }
                    }
                }

                @Override
                public void onMyError(VolleyError error) {
                    error.printStackTrace();
                    dialog.dismiss();
                }
            });

        }
    }

    public void showShareDialog() {
        dialog = new MyDialog(mActivity, String.valueOf(index.getRes().getJianjie().getCodes()), String.valueOf(index.getRes().getJianjie().getTitle()), index.getRes().getJianjie().getImgurl());
        dialog.show();//显示对话框
    }

}
