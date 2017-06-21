package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzcn77.android_app_company.Bean.GoodsBean;
import com.zzcn77.android_app_company.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class PowerCateSeachAdaptet extends BaseAdapter {
    //
    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas = datas;
    }

    private ArrayList<GoodsBean.ResBean.CateBean> datas = new ArrayList();

    public PowerCateSeachAdaptet(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ArrayList<GoodsBean.ResBean.CateBean> getItem(int position) {
        return datas;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.power_search_itemlayout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(datas.get(position).getTitle());

        if (datas.get(position).getIscheck()) {
            viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.noraml));
            viewHolder.tvTitle.setBackground(context.getResources().getDrawable(R.drawable.price_searchbgioff));
        } else {
            viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.text_check));
            viewHolder.tvTitle.setBackground(context.getResources().getDrawable(R.drawable.price_searchbg));
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
