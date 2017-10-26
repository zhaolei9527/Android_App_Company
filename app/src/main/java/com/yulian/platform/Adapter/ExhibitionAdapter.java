package com.yulian.platform.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.yulian.platform.Bean.ExhibitionBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.UrlUtils;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class ExhibitionAdapter extends BaseAdapter {
    //

    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<ExhibitionBean.ResBean> datas = new ArrayList();

    public ExhibitionAdapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ExhibitionBean.ResBean getItem(int position) {
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
            convertView = View.inflate(context, R.layout.item_the_exhibition_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.SimpleDraweeView.setImageURI(UrlUtils.BaseImg + datas.get(position).getImgurl());
        viewHolder.tvTitle.setText(datas.get(position).getTitle());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.SimpleDraweeView)
        com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
