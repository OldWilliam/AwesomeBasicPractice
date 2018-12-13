package me.jim.wx.awesomebasicpractice.widget.commentview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Date: 2018/12/13
 * Name: wx
 * Description:
 */
public class StepView extends View {
    private Paint mPaint;
    private Paint mTextPaint;

    private int mEnableColor = Color.parseColor("#04C7B7");
    private int mDisableColor = Color.parseColor("#D8D8D8");
    private int mGradient = Color.parseColor("#6604C7B7");

    private int mStep = 1;

    private List<String> mSteps = Collections.unmodifiableList(Arrays.asList("嘉宾介绍", "公布结果", "心动选择"));

    public StepView(Context context) {
        super(context);
        init();
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(28);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    public void setStep(@IntRange(from = 1) int step) {
        mStep = step;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Random random = new Random();
        setStep(Math.abs(random.nextInt() % 3) + 1);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTextPaint.setColor(mEnableColor);
        mPaint.setColor(mEnableColor);

        int width = getWidth();

        float txtHeight = mTextPaint.getFontMetrics().bottom - mTextPaint.getFontMetrics().top;
        float startStepWidth = mTextPaint.measureText(mSteps.get(0));
        float endStepWidth = mTextPaint.measureText(mSteps.get(mSteps.size() - 1));

        float lineStart = startStepWidth / 2;
        float lineEnd = width - endStepWidth / 2;
        float stepSize = (lineEnd - lineStart) / (mSteps.size() - 1);

        float space = 40;
        mPaint.setStrokeWidth(4);
        float lineTopMargin = txtHeight + space;

        mPaint.setColor(mDisableColor);
        canvas.drawLine(lineStart, lineTopMargin, lineEnd, lineTopMargin, mPaint);

        mPaint.setColor(mEnableColor);
        canvas.drawLine(lineStart, lineTopMargin, lineStart + stepSize * (mStep - 1), lineTopMargin, mPaint);

        mPaint.setColor(mGradient);
        canvas.drawCircle((mStep - 1) * stepSize + lineStart, lineTopMargin, 15, mPaint);

        for (int i = 1; i < mSteps.size() - 1; i++) {

            if (i >= mStep) {
                mTextPaint.setColor(mDisableColor);
            }

            String txt = mSteps.get(i);

            float x = i * stepSize - mTextPaint.measureText(txt) / 2;
            canvas.drawText(txt, x + lineStart, txtHeight, mTextPaint);

            canvas.drawCircle(i * stepSize + lineStart, lineTopMargin, 10, mTextPaint);
        }

        mTextPaint.setColor(mEnableColor);
        canvas.drawText(mSteps.get(0), 0, txtHeight, mTextPaint);
        canvas.drawCircle(lineStart, lineTopMargin, 10, mTextPaint);

        mTextPaint.setColor(mStep == mSteps.size() ? mEnableColor : mDisableColor);
        canvas.drawText(mSteps.get(mSteps.size() - 1), width - endStepWidth, txtHeight, mTextPaint);
        canvas.drawCircle(lineEnd, lineTopMargin, 10, mTextPaint);
    }
}
