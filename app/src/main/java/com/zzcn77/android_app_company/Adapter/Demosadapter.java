package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzcn77.android_app_company.Bean.YanShiBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.UrlUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class Demosadapter extends BaseAdapter {
    //


    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<YanShiBean.ResBean> datas = new ArrayList();

    public Demosadapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {

        return datas.get(position).getUrl();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.demo_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageURI(UrlUtils.BaseImg + datas.get(position).getImgurl());
        viewHolder.title.setText(datas.get(position).getTitle());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.imageView)
        SimpleDraweeView imageView;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
