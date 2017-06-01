package com.zzcn77.android_app_company.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzcn77.android_app_company.Acitivity.ProductSearchActivity;
import com.zzcn77.android_app_company.Adapter.PowerBrandSeachAdaptet;
import com.zzcn77.android_app_company.Adapter.PowerCateSeachAdaptet;
import com.zzcn77.android_app_company.Adapter.PowerPxSeachAdaptet;
import com.zzcn77.android_app_company.Bean.GoodsBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.WindowUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class PowersearchDialog extends Dialog {

    private static PowerCateSeachAdaptet catepowerSeachAdaptet;
    private static PowerBrandSeachAdaptet brandpowerSeachAdaptet;
    private static PowerPxSeachAdaptet pxpowerSeachAdaptet;
    private static String cid = "";
    private static String px_id = "";
    private static String start_price;
    private static String end_price;
    private static String bid = "";

    public PowersearchDialog(Context context) {
        super(context);
    }

    public PowersearchDialog(Context context, int theme) {
        super(context, theme);
        cid = "";
        px_id = "";
        bid = "";
    }

    public static class Builder implements View.OnClickListener {
        @BindView(R.id.gv_model)
        MyGridView gvModel;
        @BindView(R.id.gv_brand)
        MyGridView gvBrand;
        @BindView(R.id.et_lowprice)
        EditText etLowprice;
        @BindView(R.id.et_highprice)
        EditText etHighprice;
        @BindView(R.id.gv_resulotion)
        MyGridView gvResulotion;
        @BindView(R.id.btn_reset)
        Button btnReset;
        @BindView(R.id.btn_search)
        Button btnSearch;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.rl)
        RelativeLayout rl;
        private Context context;
        private PowersearchDialog dialog;
        private GoodsBean goodsBean;

        public Builder(Context context, GoodsBean goodsBean) {
            this.context = context;
            this.goodsBean = goodsBean;
        }

        public PowersearchDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new PowersearchDialog(context, R.style.Dialog);
            dialog.setCanceledOnTouchOutside(false);
            View inflate = inflater.inflate(R.layout.power_search_layout, null);
            ButterKnife.bind(this, inflate);
            initdata();
            initListener();
            dialog.addContentView(inflate, new LinearLayout.LayoutParams(
                    WindowUtil.getScreenWidth(context), WindowUtil.getScreenHeight(context) - WindowUtil.getStatusBarHeight(context)));

            return dialog;
        }

        private void initListener() {
            btnReset.setOnClickListener(this);
            btnSearch.setOnClickListener(this);
            rl.setOnClickListener(this);
        }

        private void initdata() {

            catepowerSeachAdaptet = new PowerCateSeachAdaptet(context, (ArrayList) goodsBean.getRes().getCate());
            brandpowerSeachAdaptet = new PowerBrandSeachAdaptet(context, (ArrayList) goodsBean.getRes().getBrand());
            pxpowerSeachAdaptet = new PowerPxSeachAdaptet(context, (ArrayList) goodsBean.getRes().getPx());

            gvBrand.setAdapter(brandpowerSeachAdaptet);
            gvModel.setAdapter(catepowerSeachAdaptet);
            gvResulotion.setAdapter(pxpowerSeachAdaptet);

            gvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for (int i = 0; i < brandpowerSeachAdaptet.getItem(position).size(); i++) {
                        if (i == position) {
                            brandpowerSeachAdaptet.getItem(position).get(i).setIscheck(!brandpowerSeachAdaptet.getItem(position).get(i).getIscheck());
                        } else {
                            brandpowerSeachAdaptet.getItem(position).get(i).setIscheck(false);
                        }
                    }
                    brandpowerSeachAdaptet.notifyDataSetChanged();
                }
            });

            gvModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for (int i = 0; i < catepowerSeachAdaptet.getItem(position).size(); i++) {
                        if (i == position) {
                            catepowerSeachAdaptet.getItem(position).get(i).setIscheck(!catepowerSeachAdaptet.getItem(position).get(i).getIscheck());
                        } else {
                            catepowerSeachAdaptet.getItem(position).get(i).setIscheck(false);
                        }
                    }
                    catepowerSeachAdaptet.notifyDataSetChanged();

                }
            });

            gvResulotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < pxpowerSeachAdaptet.getItem(position).size(); i++) {
                        if (i == position) {
                            pxpowerSeachAdaptet.getItem(position).get(i).setIscheck(!pxpowerSeachAdaptet.getItem(position).get(i).getIscheck());
                        } else {
                            pxpowerSeachAdaptet.getItem(position).get(i).setIscheck(false);
                        }
                    }
                    pxpowerSeachAdaptet.notifyDataSetChanged();

                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl:
                    dialog.dismiss();
                    break;
                case R.id.btn_reset:
                    for (int i = 0; i < brandpowerSeachAdaptet.getItem(0).size(); i++) {
                        brandpowerSeachAdaptet.getItem(0).get(i).setIscheck(false);
                    }
                    brandpowerSeachAdaptet.notifyDataSetChanged();
                    for (int i = 0; i < catepowerSeachAdaptet.getItem(0).size(); i++) {
                        catepowerSeachAdaptet.getItem(0).get(i).setIscheck(false);
                    }
                    catepowerSeachAdaptet.notifyDataSetChanged();
                    for (int i = 0; i < pxpowerSeachAdaptet.getItem(0).size(); i++) {
                        pxpowerSeachAdaptet.getItem(0).get(i).setIscheck(false);
                    }
                    pxpowerSeachAdaptet.notifyDataSetChanged();
                    etHighprice.setText("");
                    etLowprice.setText("");
                    break;
                case R.id.btn_search:
                    for (int i = 0; i < brandpowerSeachAdaptet.getItem(0).size(); i++) {
                        if (brandpowerSeachAdaptet.getItem(0).get(i).getIscheck()) {
                            bid = brandpowerSeachAdaptet.getItem(0).get(i).getId();
                        }
                    }
                    brandpowerSeachAdaptet.notifyDataSetChanged();
                    for (int i = 0; i < catepowerSeachAdaptet.getItem(0).size(); i++) {
                        if (catepowerSeachAdaptet.getItem(0).get(i).getIscheck()) {
                            cid = catepowerSeachAdaptet.getItem(0).get(i).getId();
                        }

                    }
                    catepowerSeachAdaptet.notifyDataSetChanged();
                    for (int i = 0; i < pxpowerSeachAdaptet.getItem(0).size(); i++) {
                        if (pxpowerSeachAdaptet.getItem(0).get(i).getIscheck()) {
                            px_id = pxpowerSeachAdaptet.getItem(0).get(i).getId();
                        }
                    }
                    if (etLowprice.getText().toString().isEmpty()) {
                        start_price = "0";
                    } else {
                        start_price = etLowprice.getText().toString().trim();
                    }

                    if (etHighprice.getText().toString().isEmpty()) {
                        end_price = String.valueOf(Integer.MAX_VALUE);
                    } else {
                        end_price = etHighprice.getText().toString().trim();
                    }

                    dialog.dismiss();
                    Intent intent = new Intent(context, ProductSearchActivity.class);
                    Intent intent1 = new Intent();
                    intent1.setAction("PoswerSearch");
                    if (!cid.isEmpty()) {
                        intent.putExtra("cid", cid);
                        intent1.putExtra("cid", cid);

                    }
                    if (!bid.isEmpty()) {
                        intent.putExtra("bid", bid);
                        intent1.putExtra("bid", bid);

                    }
                    if (!px_id.isEmpty()) {
                        intent.putExtra("px_id", px_id);
                        intent1.putExtra("px_id", px_id);

                    }
                    intent.putExtra("start_price", start_price);
                    intent1.putExtra("start_price", start_price);
                    intent.putExtra("end_price", end_price);
                    intent1.putExtra("end_price", end_price);
                    context.startActivity(intent);
                    context.sendBroadcast(intent1);
                    break;
            }
        }
    }

}