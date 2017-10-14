package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zzcn77.android_app_company.Bean.NewsBean;
import com.zzcn77.android_app_company.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class BusinessmenRecommendadapter extends BaseAdapter {
    //

    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<NewsBean.ResBean> datas = new ArrayList();

    public BusinessmenRecommendadapter(Context context, ArrayList datas) {
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
            convertView = View.inflate(context, R.layout.item_businessmen_recommend_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    static class ViewHolder {
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
