package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.google.gson.Gson;
import com.zzcn77.android_app_company.Acitivity.LoginActivity;
import com.zzcn77.android_app_company.Bean.DocollBean;
import com.zzcn77.android_app_company.Bean.Goods_ListsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.SPUtil;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        } else if (datas.get(position).getColl().equals("-1") || datas.get(position).getColl().equals("2")) {
            viewHolder.imgCollect.setBackground(context.getResources().getDrawable(R.mipmap.shoucang_off));
        }

        if (SPUtil.get(context, "id", "").equals("")) {
            viewHolder.rlMoney.setVisibility(View.GONE);
            viewHolder.rlShowmoney.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rlMoney.setVisibility(View.VISIBLE);
            viewHolder.rlShowmoney.setVisibility(View.GONE);
        }
        viewHolder.tvShowmoney.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        viewHolder.tvShowmoney.getPaint().setAntiAlias(true);//抗锯齿
        viewHolder.rlShowmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(context).setTitle(R.string.message)//设置对话框标题
                        .setMessage(R.string.Youarenotcurrentlyloggedin)//设置显示的内容
                        .setPositiveButton(R.string.loginnow, new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        dialog.dismiss();
                    }
                }).show();//在按键响应事件中显示此对话框
            }
        });

        viewHolder.tvTitle.setText(datas.get(position).getTitle());
        viewHolder.tvPrice.setText(datas.get(position).getPrice());
        viewHolder.tvModel.setText(datas.get(position).getX_num());
        viewHolder.SimpleDraweeView.setImageURI(UrlUtils.BaseImg + datas.get(position).getImg());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.imgCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, datas.get(position).getId(), Toast.LENGTH_SHORT).show();
                if (datas.get(position).getColl().equals("2") || datas.get(position).getColl().equals("-1")) {
                    finalViewHolder.imgCollect.setBackground(context.getResources().getDrawable(R.mipmap.shoucang_on));
                } else {
                    finalViewHolder.imgCollect.setBackground(context.getResources().getDrawable(R.mipmap.shoucang_off));
                }
                if (String.valueOf(SPUtil.get(context, "id", "")).isEmpty()) {
                    // TODO Auto-generated method stub
                    new AlertDialog.Builder(context).setTitle(R.string.message)//设置对话框标题
                            .setMessage(R.string.Youarenotcurrentlyloggedin)//设置显示的内容
                            .setPositiveButton(R.string.loginnow, new DialogInterface.OnClickListener() {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {//添加返回按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//响应事件
                            dialog.dismiss();
                        }
                    }).show();//在按键响应事件中显示此对话框
                } else {
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl + "docoll", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            String decode = Utils.decode(s);
                            DocollBean docollBean = new Gson().fromJson(decode, DocollBean.class);
                            if (docollBean.getStu().equals("1")) {
                                if (datas != null) {
                                    if (datas.get(position).getColl().equals("1")) {
                                        if (context != null)
                                            Toast.makeText(context, R.string.Collectionofsuccess, Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (context != null)
                                            Toast.makeText(context, context.getString(R.string.cancelcollection), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                if (context != null)
                                    EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            volleyError.printStackTrace();
                            if (context != null)
                                EasyToast.showShort(context, context.getString(R.string.Networkexception));
                        }
                    })

                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("key", UrlUtils.key);
                            map.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                            map.put("gid", String.valueOf(datas.get(position).getId()));
                            map.put("stu", datas.get(position).getColl());
                            return map;
                        }
                    };
                    boolean connected = Utils.isConnected(context);
                    if (connected) {
                        requestQueue.add(stringRequest);
                        if (datas.get(position).getColl().equals("1")) {
                            datas.get(position).setColl("2");
                        } else {
                            datas.get(position).setColl("1");
                        }
                    } else {
                        if (context != null)
                            EasyToast.showShort(context, context.getString(R.string.Notconnect));
                    }
                }

            }
        });
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
        @BindView(R.id.rl_money)
        RelativeLayout rlMoney;
        @BindView(R.id.tv_showmoney)
        TextView tvShowmoney;
        @BindView(R.id.rl_showmoney)
        RelativeLayout rlShowmoney;
        @BindView(R.id.img_collect)
        ImageView imgCollect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
