package com.yulian.platform.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.umeng.message.PushAgent;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.tag.TagManager;
import com.yulian.platform.Acitivity.LoginActivity;
import com.yulian.platform.Acitivity.MainActivity;
import com.yulian.platform.Bean.IndexBean;
import com.yulian.platform.Bean.SXBean;
import com.yulian.platform.Bean.ShcollBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.Volley.VolleyInterface;
import com.yulian.platform.Volley.VolleyRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class MerchantsCollectionAdapter extends BaseAdapter {
    //
    private Activity context;

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

    public MerchantsCollectionAdapter(Activity context, ArrayList datas, RelativeLayout rll, LinearLayout llEmpty, ListView listView) {
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

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.tvHao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shpingjia("1", position);
                finalViewHolder.tvHao.setTextColor(context.getResources().getColor(R.color.holo_red_light));
                finalViewHolder.tvZhong.setTextColor(context.getResources().getColor(R.color.text_check));
                finalViewHolder.tvCha.setTextColor(context.getResources().getColor(R.color.text_check));
            }
        });

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.tvZhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shpingjia("2", position);
                finalViewHolder1.tvHao.setTextColor(context.getResources().getColor(R.color.text_check));
                finalViewHolder1.tvZhong.setTextColor(context.getResources().getColor(R.color.holo_red_light));
                finalViewHolder1.tvCha.setTextColor(context.getResources().getColor(R.color.text_check));
            }
        });

        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.tvCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shpingjia("3", position);
                finalViewHolder2.tvHao.setTextColor(context.getResources().getColor(R.color.text_check));
                finalViewHolder2.tvZhong.setTextColor(context.getResources().getColor(R.color.text_check));
                finalViewHolder2.tvCha.setTextColor(context.getResources().getColor(R.color.holo_red_light));
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> params = new HashMap<>();
                params.put("key", UrlUtils.key);
                params.put("stu", "2");
                params.put("sh_id", datas.get(position).getSh_id());
                params.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
                VolleyRequest.RequestPost(context, UrlUtils.BaseUrl21 + "do_shcoll", "do_shcoll", params, new VolleyInterface(context) {
                    @Override
                    public void onMySuccess(String result) {
                        String decode = Utils.decode(result);
                        Log.d("TheEntranceActivity", decode);
                        if (decode.isEmpty()) {
                            EasyToast.showShort(context, context.getString(R.string.Networkexception));
                        } else {
                            SXBean sxBean = new Gson().fromJson(decode, SXBean.class);
                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    PushAgent.getInstance(context).getTagManager().delete(new TagManager.TCallBack() {
                                        @Override
                                        public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                                            context.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
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
                                        }
                                    }, String.valueOf(datas.get(position).getSh_id()));
                                }
                            }.start();

                        }


                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        error.printStackTrace();
                        EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
                    }
                });


            }
        });

        viewHolder.tvTitle.setText(datas.get(position).getCompany());
        viewHolder.imgConllect.setImageURI(UrlUtils.BaseImg + datas.get(position).getImg());
        viewHolder.tvFenshu.setText(datas.get(position).getPoint());
        viewHolder.tvContent.setText(datas.get(position).getDescription());
        viewHolder.imgConllect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getindex(datas.get(position).getSh_id());
            }
        });


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

        viewHolder.imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changSx("x", position);
                ShcollBean.ResBean resBean = datas.get(position);
                datas.add(position + 2, resBean);
                datas.remove(position);
                notifyDataSetChanged();
            }
        });

        viewHolder.imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changSx("s", position);
                ShcollBean.ResBean resBean = datas.get(position);
                datas.add(position - 1, resBean);
                datas.remove(position + 1);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void shpingjia(String pingjia, int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("key", UrlUtils.key);
        params.put("stu", pingjia);
        params.put("sh_id", datas.get(position).getSh_id());
        params.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BaseUrl21 + "sh_pj", "sh_pj", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = Utils.decode(result);
                Log.d("TheEntranceActivity", decode);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, context.getString(R.string.Networkexception));
                } else {
                    SXBean sxBean = new Gson().fromJson(decode, SXBean.class);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
            }
        });
    }

    private Dialog dialog;

    private void getindex(final String shid) {
        SPUtil.remove(context, "index");
        SPUtil.remove(context, "demo");
        SPUtil.remove(context, "product");
        SPUtil.remove(context, "scheme");
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        SPUtil.putAndApply(context, "shid", String.valueOf(shid));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "index", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                if (decode.isEmpty()) {
                    dialog.dismiss();
                    if (context != null)
                        EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
                } else {
                    IndexBean indexBean = new Gson().fromJson(decode, IndexBean.class);
                    if (indexBean.getStu().equals("1")) {
                        if (context != null) {
                            dialog.dismiss();
                            SPUtil.putAndApply(context, "index", s);
                            context.startActivity(new Intent(context, MainActivity.class));
                            context.finish();
                        }
                    } else {
                        dialog.dismiss();
                        if (context != null)
                            EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                volleyError.printStackTrace();
                if (context != null)
                    EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", UrlUtils.key);
                map.put("sh_id", shid);
                String id = (String) SPUtil.get(context, "id", "");
                if (!id.isEmpty()) {
                    map.put("uid", id);
                }
                return map;
            }
        };

        if (Utils.isConnected(context)) {
            requestQueue.add(stringRequest);
        } else {
            if (context != null) {
                EasyToast.showShort(context, context.getString(R.string.Notconnect));
                context.startActivity(new Intent(context, LoginActivity.class));
                context.finish();
            }
        }

    }

    private void changSx(String s, int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("key", UrlUtils.key);
        params.put("qh", s);
        params.put("id", datas.get(position).getId());
        params.put("uid", String.valueOf(SPUtil.get(context, "id", "")));
        VolleyRequest.RequestPost(context, UrlUtils.BaseUrl22 + "sx", "sx", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = Utils.decode(result);
                Log.d("TheEntranceActivity", decode);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, context.getString(R.string.Networkexception));
                } else {
                    SXBean sxBean = new Gson().fromJson(decode, SXBean.class);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                EasyToast.showShort(context, context.getString(R.string.Abnormalserver));
            }
        });
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
