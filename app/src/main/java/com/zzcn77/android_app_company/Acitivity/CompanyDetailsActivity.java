package com.zzcn77.android_app_company.Acitivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.tencent.smtt.sdk.WebSettings;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.DensityUtils;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.Utils;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/18.
 */

public class CompanyDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.RollPagerView)
    com.jude.rollviewpager.RollPagerView RollPagerView;
    @BindView(R.id.forum_context)
    com.tencent.smtt.sdk.WebView forumContext;
    @BindView(R.id.img_back)
    ImageView imgBack;

    public static String html = "<p>请仔细阅读本协议，App平台将依据以下条件和条款为您提供服务。<br/><br/>欢迎阅读App平台用户协议(下称“本协议”)。本协议阐述之条款和条件适用于您使用App平台所提供的各种工具和服务(下称“服务”)<br/><br/>1．服务条款的确认平台根据本服务条款及对该条款的修改向用户提供服务。本服务条款具有合同法上的法律效力。<br/>如果您对协议的任何条款表示异议，您可以选择不注册，一旦您点选“注册”并通过注册程序，即表示您自愿接受本协议之所有条款，并已成为App平台的注册会员。用户在使用App平台的同时，同意接受App平台会员服务提供的各类信息服务<br/></p><p><br/></p><p><img src=\"/ueditor/php/upload/image/20161217/1481957560686270.png\" title=\"1481957560686270.png\" alt=\"屏幕快照 2016-12-17 下午2.51.17.png\"/></p><p>2.符合下列条件之一的个人、组织，才能申请成为App平台用户、使用本协议项下的服务：<br/>3.年满十八周岁，并具有民事权利能力和民事行为能力的自然人；<br/>4. 未满十八周岁，但监护人（包括但不仅限于父母）予以书面同意的自然人；<br/>5. 根据中国法律、法规成立并合法存在的公司等企业法人、事业单位、社团组织和其他组织。无民事行为能力人、限制民事行为能力人以及无经营或特定经营资格的组织不当注册为用户的，其与本公司之间的协议自始无效，本公司一经发现，有权立即注销该用户。<br/>6.用户有权按照App平台规定的程序和要求使用App平台向会员提供的各项网络服务，如果会员对该服务有异议，可以与潮App平台联系以便得到及时解决。<br/>7. 用户在申请使用App平台网络服务时，必须向App平台提供准确的个人资料，如个人资料有任何变动，必须及时更新。<br/>8. 用户须同意接受App平台通过电子邮件或其他方式向会员发送相关商业信息。<br/><br/></p>";

    @Override
    protected int setthislayout() {
        return R.layout.company_details_layout;
    }


    private class LoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.drawable.i_banner,
                R.drawable.i_banner,
                R.drawable.i_banner,
                R.drawable.i_banner,
        };

        public LoopAdapter(com.jude.rollviewpager.RollPagerView viewPager) {
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

    @Override
    protected void initview() {

        RollPagerView.setHintView(new IconHintView(context, R.drawable.shape_selected, R.drawable.shape_noraml, DensityUtils.dp2px(context, 20)));
        RollPagerView.setAdapter(new LoopAdapter(RollPagerView));
        RollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EasyToast.showShort(context, "position" + position);
            }
        });
        RollPagerView.setPlayDelay(3000);
        if (html.contains("img src=\"")) {
            html = html.replace("img src=\"", "img src=\"http://1415.tt.100help.net");
        }
        String sb = "<html><head></head><body>" + html +
                "</body></html>";
        forumContext.getSettings().setJavaScriptEnabled(true);// 支持js
        int screenDensity = getResources().getDisplayMetrics().densityDpi;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM;
        switch (screenDensity) {
            case DisplayMetrics.DENSITY_LOW:
                zoomDensity = WebSettings.ZoomDensity.CLOSE;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                zoomDensity = WebSettings.ZoomDensity.MEDIUM;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                zoomDensity = WebSettings.ZoomDensity.FAR;
                break;
        }
        forumContext.getSettings().setDefaultZoom(zoomDensity);
        forumContext.getSettings().setLoadWithOverviewMode(true);
        forumContext.getSettings().setUseWideViewPort(true);
        forumContext.loadDataWithBaseURL("about:blank", Utils.getNewContent(sb), "text/html", "utf-8", null);

    }


    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

        }

    }
}
