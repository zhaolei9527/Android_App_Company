package com.zzcn77.android_app_company.Acitivity;

import android.graphics.drawable.Animatable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;

import butterknife.BindView;
import me.relex.photodraweeview.OnPhotoTapListener;


public class BigImageActivity extends BaseActivity {
    @BindView(R.id.PhotoDraweeView)
    me.relex.photodraweeview.PhotoDraweeView PhotoDraweeView;
    @BindView(R.id.rl)
    RelativeLayout rl;

    @Override
    protected void ready() {
        super.ready();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        PhotoDraweeView.setOnPhotoTapListener(new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                finish();
            }
        });


        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

}
