package com.yulian.platform.Acitivity;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.google.gson.Gson;
import com.yulian.platform.App;
import com.yulian.platform.Base.BaseActivity;
import com.yulian.platform.Bean.B_DetailBean;
import com.yulian.platform.Bean.IndexBean;
import com.yulian.platform.R;
import com.yulian.platform.Utils.BitmapUtil;
import com.yulian.platform.Utils.EasyToast;
import com.yulian.platform.Utils.SPUtil;
import com.yulian.platform.Utils.UrlUtils;
import com.yulian.platform.Utils.Utils;
import com.yulian.platform.Volley.VolleyInterface;
import com.yulian.platform.Volley.VolleyRequest;

import java.util.HashMap;
import java.util.Map;

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
        btnGotomain.setOnClickListener(this);
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
                        private Bitmap bitmap;

                        @Override
                        public String getName() {
                            return "redMeshPostprocessor";
                        }

                        @Override
                        public void process(Bitmap bitmap) {
                            Canvas canvas = new Canvas(bitmap);// 设置canvas画布背景为白色
                            this.bitmap = BitmapUtil.drawable2Bitmap(getResources().getDrawable(R.drawable.zuobiao));
                            int height = this.bitmap.getHeight();
                            int width = this.bitmap.getWidth();
                            canvas.drawBitmap(this.bitmap, (int) (x * bitmap.getWidth()) - (width / 2), (int) (y * bitmap.getHeight()) - height, null);
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
            case R.id.btn_gotomain:
                if (b_detailBean.getRes().getQ_num().isEmpty()) {
                    Toast.makeText(context, getResources().getString(R.string.Businessisnotonline), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(b_detailBean.getRes().getQ_num().equals("0")){
                    Toast.makeText(context, getResources().getString(R.string.Businessisnotonline), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (b_detailBean.getRes().getSid().isEmpty()) {
                    Toast.makeText(context, getResources().getString(R.string.Businessisnotonline), Toast.LENGTH_SHORT).show();
                    return;
                }

                if(b_detailBean.getRes().getSid().equals("0")){
                    Toast.makeText(context, getResources().getString(R.string.Businessisnotonline), Toast.LENGTH_SHORT).show();
                    return;
                }


                dialog.show();
                getindex(b_detailBean.getRes().getSid());
                break;
        }
    }

    private void getindex(final String shid) {
        SPUtil.remove(context, "index");
        SPUtil.remove(context, "demo");
        SPUtil.remove(context, "product");
        SPUtil.remove(context, "scheme");

        SPUtil.putAndApply(context, "shid", String.valueOf(shid));
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtils.BaseUrl21 + "index", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String decode = Utils.decode(s);
                Log.d("ExhibitionDetailsActivi", decode);
                if (decode.isEmpty()) {
                    dialog.dismiss();
                    if (context != null)
                        EasyToast.showShort(context, getString(R.string.Abnormalserver));
                } else {
                    IndexBean indexBean = new Gson().fromJson(decode, IndexBean.class);
                    if (indexBean.getStu().equals("1")) {
                        if (context != null) {
                            dialog.dismiss();
                            SPUtil.putAndApply(context, "index", s);
                            startActivity(new Intent(context, MainActivity.class));
                        }
                    } else {
                        dialog.dismiss();
                        if (context != null)
                            EasyToast.showShort(context, getString(R.string.Abnormalserver));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                volleyError.printStackTrace();
                if (context != null)
                    EasyToast.showShort(context, getString(R.string.Abnormalserver));
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
                EasyToast.showShort(context, getString(R.string.Notconnect));
                startActivity(new Intent(context, LoginActivity.class));
                finish();
            }
        }

    }

}
