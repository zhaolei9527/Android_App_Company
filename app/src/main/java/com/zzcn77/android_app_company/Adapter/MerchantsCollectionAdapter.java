package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzcn77.android_app_company.Bean.ShcollBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.UrlUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class MerchantsCollectionAdapter extends BaseAdapter {
    //
    private Context context;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<ShcollBean.ResBean> datas = new ArrayList();

    private RelativeLayout rll;
    private LinearLayout llEmpty;
    private ListView listView;

    public MerchantsCollectionAdapter(Context context, ArrayList datas, RelativeLayout rll, LinearLayout llEmpty, ListView listView) {
        this.context = context;
        this.datas = datas;
        this.rll = rll;
        this.llEmpty = llEmpty;
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ShcollBean.ResBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_merchants_collection_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.imgUp.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imgUp.setVisibility(View.VISIBLE);
        }

        if (position == datas.size() - 1) {
            viewHolder.imgDown.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imgDown.setVisibility(View.VISIBLE);
        }

        viewHolder.imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "up" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "down" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.tvHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hao" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.tvZhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Zhong" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.tvCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cha" + position, Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete" + position, Toast.LENGTH_SHORT).show();
                datas.remove(position);
                MerchantsCollectionAdapter.this.notifyDataSetChanged();
                if (datas.size() == 0) {
                    if (rll != null) {
                        rll.setVisibility(View.GONE);
                        llEmpty.setVisibility(View.VISIBLE);
                        listView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        viewHolder.tvTitle.setText(datas.get(position).getCompany());
        viewHolder.imgConllect.setImageURI(UrlUtils.BaseImg + datas.get(position).getImg());
        viewHolder.tvFenshu.setText(datas.get(position).getPoint() + "分");
        viewHolder.tvContent.setText(datas.get(position).getDescription());

        if (datas.get(position).getPj_stu().equals("0")) {
            viewHolder.tvHao.setTextColor(context.getResources().getColor(R.color.text_check));
            viewHolder.tvZhong.setTextColor(context.getResources().getColor(R.color.text_check));
            viewHolder.tvCha.setTextColor(context.getResources().getColor(R.color.text_check));
        } else if (datas.get(position).getPj_stu().equals("1")) {
            viewHolder.tvHao.setTextColor(context.getResources().getColor(R.color.holo_red_light));
            viewHolder.tvZhong.setTextColor(context.getResources().getColor(R.color.text_check));
            viewHolder.tvCha.setTextColor(context.getResources().getColor(R.color.text_check));
        } else if (datas.get(position).getPj_stu().equals("2")) {
            viewHolder.tvZhong.setTextColor(context.getResources().getColor(R.color.holo_red_light));
            viewHolder.tvHao.setTextColor(context.getResources().getColor(R.color.text_check));
            viewHolder.tvCha.setTextColor(context.getResources().getColor(R.color.text_check));
        } else if (datas.get(position).getPj_stu().equals("3")) {
            viewHolder.tvCha.setTextColor(context.getResources().getColor(R.color.holo_red_light));
            viewHolder.tvHao.setTextColor(context.getResources().getColor(R.color.text_check));
            viewHolder.tvZhong.setTextColor(context.getResources().getColor(R.color.text_check));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_conllect)
        SimpleDraweeView imgConllect;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_pingfen)
        TextView tvPingfen;
        @BindView(R.id.tv_fenshu)
        TextView tvFenshu;
        @BindView(R.id.ll_pingfen)
        LinearLayout llPingfen;
        @BindView(R.id.View)
        android.view.View View;
        @BindView(R.id.tv_hao)
        Button tvHao;
        @BindView(R.id.ll_hao)
        LinearLayout llHao;
        @BindView(R.id.tv_zhong)
        Button tvZhong;
        @BindView(R.id.ll_zhong)
        LinearLayout llZhong;
        @BindView(R.id.tv_cha)
        Button tvCha;
        @BindView(R.id.ll_cha)
        LinearLayout llCha;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.img_up)
        Button imgUp;
        @BindView(R.id.img_down)
        Button imgDown;

        ViewHolder(android.view.View view) {
            ButterKnife.bind(this, view);
        }
    }
}
