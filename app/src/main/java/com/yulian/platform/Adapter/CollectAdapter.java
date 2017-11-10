package com.yulian.platform.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yulian.platform.Bean.CollBean;
import com.yulian.platform.Bean.DocollBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class CollectAdapter extends BaseAdapter {
    //

    private Context context;
    private Dialog dialog;

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas.addAll(datas);
    }

    private ArrayList<CollBean.ResBean> datas = new ArrayList();

    private RelativeLayout rll;
    private LinearLayout llEmpty;

    public CollectAdapter(Context context, ArrayList datas, RelativeLayout rll, LinearLayout llEmpty) {
        this.context = context;
        this.datas = datas;
        this.rll = rll;
        this.llEmpty = llEmpty;
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
            convertView = View.inflate(context, R.layout.conllect_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvrmb.setText(datas.get(position).getRmb());
        viewHolder.imgConllect.setImageURI(UrlUtils.BaseImg + datas.get(position).getImg());
        viewHolder.tvModel.setText(context.getString(R.string.model) + datas.get(position).getX_num());
        viewHolder.tvPrice.setText(datas.get(position).getPrice());
        viewHolder.tvTitle.setText(datas.get(position).getTitle());
        viewHolder.tv_Company.setText(datas.get(position).getSname());
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = Utils.showLoadingDialog(context);
                dialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "docoll", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        dialog.dismiss();
                        String decode = Utils.decode(s);
                        DocollBean docollBean = new Gson().fromJson(decode, DocollBean.class);
                        if (docollBean.getStu().equals("1")) {
                            Toast.makeText(context, context.getString(R.string.cancelcollection), Toast.LENGTH_SHORT).show();
                            datas.remove(position);
                            notifyDataSetChanged();
                            if (datas.size() == 0) {
                                if (rll != null) {
                                    rll.setVisibility(View.GONE);
                                    llEmpty.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            //EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        volleyError.printStackTrace();
                        EasyToast.showShort(context, context.getString(R.string.Networkexception));
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("key", UrlUtils.key);
                        map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                        map.put("gid", String.valueOf(datas.get(position).getGid()));
                        map.put("stu", String.valueOf(2));
                        return map;
                    }
                };
                boolean connected = Utils.isConnected(context);
                if (connected) {
                    requestQueue.add(stringRequest);
                } else {
                    EasyToast.showShort(context, context.getString(R.string.Notconnect));
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_conllect)
        SimpleDraweeView imgConllect;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_model)
        TextView tvModel;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.rmb)
        TextView tvrmb;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.tv_Company)
        TextView tv_Company;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
