package com.zzcn77.android_app_company.Acitivity;

import android.view.Window;
import android.view.WindowManager;

import com.github.barteksc.pdfviewer.PDFView;
import com.zzcn77.android_app_company.Base.BaseActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.IntentUtil;

import java.io.File;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/31.
 */

public class PdfActivity extends BaseActivity  {
    @BindView(R.id.pdfView)
    PDFView pdfView;
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
        return R.layout.pdf_layout;
    }

    @Override
    protected void initview() {

        if (!IntentUtil.isBundleEmpty(getIntent())) {
            String file = getIntent().getStringExtra("file");
            pdfView.fromFile(new File(file))
                    .defaultPage(0)
                    .enableAnnotationRendering(true)
                    .load();
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

}
