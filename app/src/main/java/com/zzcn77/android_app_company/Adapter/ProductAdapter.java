package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/23.
 */

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList datas = new ArrayList();

    public ProductAdapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;
    }

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.scheme_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Utils.displayImageFresco(R.drawable.tu, viewHolder.imgScheme);
        viewHolder.tvTitle.setText(String.valueOf(position));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_scheme)
        SimpleDraweeView imgScheme;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
