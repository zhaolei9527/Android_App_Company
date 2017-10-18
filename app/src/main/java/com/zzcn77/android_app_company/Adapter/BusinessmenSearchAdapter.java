package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzcn77.android_app_company.Bean.ShopsBean;
import com.zzcn77.android_app_company.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class BusinessmenSearchAdapter extends BaseAdapter {
    //

    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<ShopsBean.ResBean> datas = new ArrayList();

    public BusinessmenSearchAdapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ShopsBean.ResBean getItem(int position) {
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
            convertView = View.inflate(context, R.layout.item_businessmen_search_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCompanyHangye.setText(datas.get(position).getTitle());
        viewHolder.tvCompanyDaihao.setText(datas.get(position).getCodes());
        viewHolder.tvCompanyTitle.setText(datas.get(position).getName());
        viewHolder.tvCompanyPingfen.setText(datas.get(position).getPoint());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_company_title)
        TextView tvCompanyTitle;
        @BindView(R.id.tv_company_hangye)
        TextView tvCompanyHangye;
        @BindView(R.id.tv_company_pingfen)
        TextView tvCompanyPingfen;
        @BindView(R.id.tv_company_daihao)
        TextView tvCompanyDaihao;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
