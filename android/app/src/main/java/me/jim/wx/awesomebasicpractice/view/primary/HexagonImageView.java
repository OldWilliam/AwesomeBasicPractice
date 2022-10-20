package me.jim.wx.awesomebasicpractice.view.primary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by wx on 2017/12/14.
 * <p>
 * 自定义圆形ImageView
 * http://www.jianshu.com/p/4f55200cea14
 * <p>
 * 可对比看看v4包是怎么实现的，使用Shape和ShapeDrawable，所以Drawable可以值得研究的
 * android.support.v4.widget.CircleImageView
 */

public class HexagonImageView extends androidx.appcompat.widget.AppCompatImageView {
    public HexagonImageView(Context context) {
        super(context);
    }

    public HexagonImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        //src未设的情况
        if (drawable == null) {
            return;
        }

        //去掉异常情况
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }

        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        if (null == b) {
            return;
        }
        //TODO isMutable是啥意思？
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        int radius = getWidth();
        Bitmap roundBitmap = getCroppedBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

    private static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        //比较初始bitmap宽高和给定的圆形直径，判断是否需要缩放裁剪Bitmap对象
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);// TODO: 2017/12/14 filter是啥意思？
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);// TODO: 2017/12/14  创建和Bitmap一样大的Canvas？
        final Paint paint = createPaint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        /**
         * 背景图
         */
        canvas.drawARGB(0, 0, 0, 0);
        //圆形背景
        //        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
        //                sbmp.getHeight() / 2 + 0.7f,
        //                sbmp.getWidth() / 2 + 0.1f,
        //                paint);
        /**
         * 0,radius/4
         * radius/4,0
         * radius/4*3,0
         * radius,radius/4
         * radius,radius/4*3
         * radius/4*3,radius
         * radius/4,radius
         * 0,radius/4*3
         */
        Path path = new Path();
        path.lineTo(0, radius / 3);
        path.lineTo(radius / 3, 0);
        path.lineTo(radius / 3 * 2, 0);
        path.lineTo(radius, radius / 3);
        path.lineTo(radius, radius / 3 * 2);
        path.lineTo(radius / 3 * 2, radius);
        path.lineTo(radius / 3, radius);
        path.lineTo(0, radius / 3 * 2);
        canvas.drawPath(path, paint);
        //核心部分，设置两张图片的相交模式，在这里就是上面绘制的Circle和下面绘制的Bitmap,使前景图和和背景图相交
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// TODO: 2017/12/14

        /**
         * 前景图
         */
        canvas.drawBitmap(sbmp, rect, rect, paint);
        return output;
    }

    private static Paint createPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // TODO: 2017/12/14
        paint.setFilterBitmap(true);
        // TODO: 2017/12/14
        paint.setDither(true);
        paint.setColor(Color.parseColor("#BAB399"));
        return paint;
    }
}
