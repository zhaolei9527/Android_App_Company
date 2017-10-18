package com.zzcn77.android_app_company.Acitivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.google.gson.Gson;
import com.zzcn77.android_app_company.App;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.Bean.B_DetailBean;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.BitmapUtil;
import com.zzcn77.android_app_company.Utils.EasyToast;
import com.zzcn77.android_app_company.Utils.UrlUtils;
import com.zzcn77.android_app_company.Utils.Utils;
import com.zzcn77.android_app_company.Volley.VolleyInterface;
import com.zzcn77.android_app_company.Volley.VolleyRequest;

import java.util.HashMap;

import butterknife.BindView;

public class ExhibitionDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.SimpleDraweeView)
    com.facebook.drawee.view.SimpleDraweeView SimpleDraweeView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_zhanwei)
    TextView tvZhanwei;
    @BindView(R.id.tv_zhanting)
    TextView tvZhanting;
    @BindView(R.id.btn_gotomain)
    Button btnGotomain;
    @BindView(R.id.SDW_exhibition)
    com.facebook.drawee.view.SimpleDraweeView SDWExhibition;
    private Dialog dialog;
    private B_DetailBean b_detailBean;
    private double x;
    private double y;

    @Override
    protected int setthislayout() {
        return R.layout.activity_exhibition_details;
    }

    @Override
    protected void initview() {
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        SDWExhibition.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        App.queues.cancelAll("b_detail");
        super.onDestroy();
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("id");
        dialog = Utils.showLoadingDialog(context);
        dialog.show();
        HashMap<String, String> params = new HashMap<>();
        params.put("key", UrlUtils.key);
        params.put("id", id);
        VolleyRequest.RequestPost(context, UrlUtils.BaseUrl22 + "b_detail", "b_detail", params, new VolleyInterface(context) {
            @Override
            public void onMySuccess(String result) {
                String decode = Utils.decode(result);
                Log.d("ExhibitionDetails", decode);
                if (decode.isEmpty()) {
                    EasyToast.showShort(context, getString(R.string.Networkexception));
                } else {
                    b_detailBean = new Gson().fromJson(decode, B_DetailBean.class);
                    x = Double.parseDouble(b_detailBean.getRes().getX()) / 100;
                    y = Double.parseDouble(b_detailBean.getRes().getY()) / 100;
                    SimpleDraweeView.setImageURI(UrlUtils.BaseImg + b_detailBean.getRes().getImg());
                    tvTitle.setText(b_detailBean.getRes().getTitle());
                    tvZhanting.setText(b_detailBean.getRes().getName());
                    tvZhanwei.setText(b_detailBean.getRes().getNumber());
                    Postprocessor redMeshPostprocessor = new BasePostprocessor() {
                        @Override
                        public String getName() {
                            return "redMeshPostprocessor";
                        }

                        @Override
                        public void process(Bitmap bitmap) {
                            Canvas canvas = new Canvas(bitmap);// 设置canvas画布背景为白色
                            canvas.drawBitmap(BitmapUtil.drawable2Bitmap(getResources().getDrawable(R.mipmap.shoucang_sj3)), (int) (x * bitmap.getWidth()), (int) (y * bitmap.getHeight()), null);
                        }
                    };
                    ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(UrlUtils.BaseImg + b_detailBean.getRes().getImgurl()))
                            .setPostprocessor(redMeshPostprocessor)
                            .build();

                    PipelineDraweeController controller = (PipelineDraweeController)
                            Fresco.newDraweeControllerBuilder()
                                    .setImageRequest(request)
                                    .setOldController(SDWExhibition.getController())
                                    // other setters as you need
                                    .build();
                    SDWExhibition.setController(controller);

                    dialog.dismiss();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.SDW_exhibition:
                startActivity(new Intent(context, BigImageActivity.class)
                        .putExtra("x", x)
                        .putExtra("y", y)
                        .putExtra("imgurl", UrlUtils.BaseImg + b_detailBean.getRes().getImgurl()));
                break;
        }
    }
}
