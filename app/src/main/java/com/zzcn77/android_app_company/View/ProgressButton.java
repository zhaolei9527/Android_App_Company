package com.zzcn77.android_app_company.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zzcn77.android_app_company.R;

/**
 * Created by 赵磊 on 2017/5/27.
 */

public class ProgressButton extends android.support.v7.widget.AppCompatButton {

    private int mProgress;

    public ProgressButton(Context context) {
        super(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        mPaint.setColor(getContext().getResources().getColor(R.color.purple_progress));
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(128);
        mPaint.setStrokeWidth(1.0f);
        Rect rect = new Rect();
        //先获取Button的边框
        canvas.getClipBounds(rect);
        rect.left += getPaddingLeft();
        //填充条的右边界根据当前进度来计算
        rect.top += getPaddingTop();
        rect.right = (rect.left - getPaddingLeft()) + (mProgress * getWidth() / 100) - getPaddingRight();
        rect.bottom -= getPaddingBottom();
        //绘制一个圆角的长条，这样相对好看一点
        canvas.drawRoundRect(new RectF(rect), 8.0f, 8.0f, mPaint);
    }

    public void updateProgress(int progress) {
        if (progress >= 0 && progress <= 100) {
            mProgress = progress;
            invalidate();
        } else if (progress < 0) {
            mProgress = 0;
            invalidate();
        } else if (progress > 100) {
            mProgress = 100;
            invalidate();
        }
    }

}
