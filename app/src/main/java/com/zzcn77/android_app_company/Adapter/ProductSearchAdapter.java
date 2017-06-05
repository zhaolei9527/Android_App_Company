package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzcn77.android_app_company.Bean.Goods_ListsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.UrlUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/31.
 */

public class ProductSearchAdapter extends BaseAdapter {


    private Context context;

    public ArrayList<Goods_ListsBean.ResBean.GoodsBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<Goods_ListsBean.ResBean.GoodsBean> datas = new ArrayList();

    public ProductSearchAdapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.product_gv_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (datas.get(position).getColl().equals("1")) {
            viewHolder.imgCollect.setBackground(context.getResources().getDrawable(R.mipmap.shoucang_on));
        } else if (datas.get(position).getColl().equals("-1")) {
            viewHolder.imgCollect.setBackground(context.getResources().getDrawable(R.mipmap.shoucang_off));
        }

        viewHolder.tvTitle.setText(datas.get(position).getTitle());
        viewHolder.tvPrice.setText(datas.get(position).getPrice());
        viewHolder.tvModel.setText(datas.get(position).getX_num());
        viewHolder.SimpleDraweeView.setImageURI(UrlUtils.BaseImg + datas.get(position).getImg());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.SimpleDraweeView)
        com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_model)
        TextView tvModel;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.img_collect)
        ImageView imgCollect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
