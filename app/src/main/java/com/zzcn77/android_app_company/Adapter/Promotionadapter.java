package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zzcn77.android_app_company.Bean.IndexBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class Promotionadapter extends BaseAdapter {


    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas = datas;
    }

    private ArrayList<IndexBean.ResBean.HuodongBean> datas = new ArrayList();

    public Promotionadapter(Context context, List datas) {
        this.context = context;
        this.datas = (ArrayList) datas;

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public String getItem(int position) {
        return datas.get(position).getId();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.promotion_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.DraweeView.setImageURI(UrlUtils.BaseImg+datas.get(position).getImgurl());
        viewHolder.tv_title.setText(datas.get(position).getTitle());
        viewHolder.tv_message.setText(datas.get(position).getKeywords());
        viewHolder.tv_dis.setText(datas.get(position).getZhe()+context.getString(R.string.discount));
        return convertView;
    }

    public class ViewHolder {
        public View rootView;
        public com.facebook.drawee.view.SimpleDraweeView DraweeView;
        public FrameLayout fl;
        public TextView tv_title;
        public TextView tv_message;
        public TextView tv_dis;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.DraweeView = (com.facebook.drawee.view.SimpleDraweeView) rootView.findViewById(R.id.SimpleDraweeView);
            this.fl = (FrameLayout) rootView.findViewById(R.id.fl);
            this.tv_title = (TextView) rootView.findViewById(R.id.tv_title);
            this.tv_message = (TextView) rootView.findViewById(R.id.tv_message);
            this.tv_dis = (TextView) rootView.findViewById(R.id.tv_dis);
        }

    }
}
