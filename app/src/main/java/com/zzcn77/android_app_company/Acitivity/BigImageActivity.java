package com.zzcn77.android_app_company.Acitivity;

import android.graphics.drawable.Animatable;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;

import butterknife.BindView;


public class BigImageActivity extends BaseActivity {
    @BindView(R.id.PhotoDraweeView)
    me.relex.photodraweeview.PhotoDraweeView PhotoDraweeView;
    @Override
    protected void ready() {
        super.ready();
       /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
    @Override
    protected int setthislayout() {
        return R.layout.activity_big_image;
    }

    @Override
    protected void initview() {
        String imgurl = getIntent().getStringExtra("imgurl");
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(imgurl);
        controller.setOldController(PhotoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || PhotoDraweeView == null) {
                    return;
                }
                PhotoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        PhotoDraweeView.setController(controller.build());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
