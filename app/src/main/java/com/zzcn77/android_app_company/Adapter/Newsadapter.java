package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zzcn77.android_app_company.Bean.NewsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.DateUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class Newsadapter extends BaseAdapter {


    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<NewsBean.ResBean> datas = new ArrayList();

    public Newsadapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public NewsBean.ResBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.news_content_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.imageView.setImageURI(UrlUtils.BaseImg + datas.get(position).getImgurl());
        viewHolder.title.setText(datas.get(position).getTitle());
        viewHolder.tvContent.setText(datas.get(position).getKeywords());
        viewHolder.tvTimeYear.setText(DateUtil.getDay(Long.parseLong(datas.get(position).getAdd_time())));
        viewHolder.tvTimeHour.setText(DateUtil.getMillon(Long.parseLong(datas.get(position).getAdd_time())));
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.imageView)
        SimpleDraweeView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_releaseTime)
        TextView tvReleaseTime;
        @BindView(R.id.tv_time_year)
        TextView tvTimeYear;
        @BindView(R.id.tv_time_hour)
        TextView tvTimeHour;
        @BindView(R.id.tv_Content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
