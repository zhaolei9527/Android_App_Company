package com.zzcn77.android_app_company.View;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zzcn77.android_app_company.Adapter.PowerSeachAdaptet;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.WindowUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/24.
 */

public class PowersearchDialog extends Dialog {

    public PowersearchDialog(Context context) {
        super(context);
    }

    public PowersearchDialog(Context context, int theme) {
        super(context, theme);
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
        private Context context;
        private PowersearchDialog dialog;

        public Builder(Context context) {
            this.context = context;
        }

        public PowersearchDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            dialog = new PowersearchDialog(context, R.style.Dialog);
            View inflate = inflater.inflate(R.layout.power_search_layout, null);
            ButterKnife.bind(this, inflate);
            initview(inflate);
            initdata();
            initListener();
            dialog.addContentView(inflate, new LinearLayout.LayoutParams(
                    WindowUtil.getScreenWidth(context), WindowUtil.getScreenHeight(context) - WindowUtil.getStatusBarHeight(context)));

            return dialog;
        }

        private void initListener() {
            btnReset.setOnClickListener(this);
            btnSearch.setOnClickListener(this);
        }

        private void initdata() {
            ArrayList dates = new ArrayList();
            dates.add("");
            dates.add("");
            dates.add("");
            dates.add("");
            dates.add("");
            dates.add("");

            PowerSeachAdaptet powerSeachAdaptet = new PowerSeachAdaptet(context, dates);
            gvBrand.setAdapter(powerSeachAdaptet);
            gvModel.setAdapter(powerSeachAdaptet);
            gvResulotion.setAdapter(powerSeachAdaptet);

        }

        private void initview(View inflate) {


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {


            }
        }
    }

}