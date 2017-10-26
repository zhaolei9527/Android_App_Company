package com.yulian.platform.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;
import com.yulian.platform.R;

/**
 * Created by 赵磊 on 2017/5/19.
 */

public class LoadMoreFooterView extends LinearLayout implements SwipeTrigger, SwipeLoadMoreTrigger {

    private TextView textView;

    public LoadMoreFooterView(Context context) {
        super(context);
        initview();
    }

    private void initview() {

        addView(View.inflate(getContext(), R.layout.loadmorefootview, null));
        textView = (TextView) findViewById(R.id.tv);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initview();

    }


    @Override
    public void onLoadMore() {
        textView.setText(R.string.loading);
    }

    @Override
    public void onPrepare() {
        textView.setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            if (yScrolled <= -getHeight()) {
                textView.setText(R.string.readloading);
            } else {
                textView.setText(R.string.uploading);
            }
        } else {
            textView.setText(R.string.loadingisok);
        }
    }

    @Override
    public void onRelease() {
        textView.setText(R.string.loading);
    }

    @Override
    public void onComplete() {
        textView.setText(R.string.loadingisok);
    }

    @Override
    public void onReset() {
        textView.setText(R.string.loadingisok);
    }
}