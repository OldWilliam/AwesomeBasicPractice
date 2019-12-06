package me.jim.wx.awesomebasicpractice.widget;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.support.v7.widget.RecyclerView;

import me.jim.wx.awesomebasicpractice.util.ContextHelper;

import static me.jim.wx.awesomebasicpractice.util.UtilsKt.dp2Px;

/**
 * Date: 2019-11-27
 * Name: weixin
 * Description: RecyclerView 顶部透明渐变
 */
class FadingEdgeDecoration extends RecyclerView.ItemDecoration {

    private final float H;
    private Paint mPaint = new Paint();
    private Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private LinearGradient linearGradient;
    private int layerId;
    private Matrix mMatrix = new Matrix();

    // RecyclerView.draw->RecyclerView.onDraw->ItemDecoration.onDraw->RecyclerView.dispatchDraw->ItemDecoration.onDrawOver


    /**
     * // Step 3, draw the content
     * if (!dirtyOpaque) onDraw(canvas);
     * <p>
     * // Step 4, draw the children
     * dispatchDraw(canvas);
     */

    @SuppressWarnings("WeakerAccess")
    public FadingEdgeDecoration() {
        super();
        H = dp2Px(ContextHelper.getContext(), 26);
        linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, H, new int[]{0x00FFFFFF, 0x80ffffff, 0xFFFFFFFF}, new float[]{0f, 0.6f, 1.0f}, Shader.TileMode.CLAMP);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        //就是save了一个背景，onDraw就是draw自己，绘制子View在draw里面
        layerId = canvas.saveLayer(0.0f, 0.0f, (float) parent.getWidth(), (float) parent.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        float top;
        if (parent.getTranslationY() >= 0) {
            top = 0;
        } else {
            top = -parent.getTranslationY();
        }
        mMatrix.setTranslate(0, top);
        linearGradient.setLocalMatrix(mMatrix);
        mPaint.setXfermode(xfermode);
        mPaint.setShader(linearGradient); //linearGradient 是src

        canvas.drawRect(0.0f, top, parent.getRight(), top + H, mPaint);

        mPaint.setXfermode(null);//default 是 src_over   src和dst都画，src在上面
        canvas.restoreToCount(layerId); //save的layer是dst，画的LinearGradient是src
    }
}
