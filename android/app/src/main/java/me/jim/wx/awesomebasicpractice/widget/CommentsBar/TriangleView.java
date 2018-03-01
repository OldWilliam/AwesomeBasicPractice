package me.jim.wx.awesomebasicpractice.widget.CommentsBar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by wx on 2018/3/1.
 *
 * 三角形的View，可以让图片显示成三角形
 */

public class TriangleView extends android.support.v7.widget.AppCompatImageView {
    public TriangleView(Context context) {
        super(context);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        BitmapDrawable srcDrawable = (BitmapDrawable) getDrawable();
        ColorDrawable bgDrawable = (ColorDrawable) getBackground();

        //必须要copy，不然如果有其他地方使用这个图片，然后被回收，就为空了
        Bitmap bmpCopy = null;
        if (srcDrawable != null && srcDrawable.getBitmap() != null) {
            bmpCopy = srcDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
        }
        //创建空画布
        Bitmap output = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(output);
        //如果有背景
        if (bgDrawable != null) {
            c.drawColor(bgDrawable.getColor());
        }
        //先把图片拉伸下，和控件大小一样
        if (bmpCopy != null) {
            c.drawBitmap(bmpCopy, null, new Rect(0, 0, getWidth(), getHeight()), new Paint());
        }
        //当然总是要创建画笔
        Paint paint = new Paint();
        //shader是给画笔用的, 核心代码
        BitmapShader shader = new BitmapShader(output, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        //创建路径，三角形
        Path path = new Path();
        path.lineTo(getWidth() / 2, 0);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth() / 2, 0);

        //画上去
        canvas.drawPath(path, paint);
    }
}
