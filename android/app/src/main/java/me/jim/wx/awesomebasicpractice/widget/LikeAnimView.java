package me.jim.wx.awesomebasicpractice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

/**
 * Date: 2019-09-20
 * Name: wx
 * Description: 仿直播间飘心动画
 */
public class LikeAnimView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawHandler mDrawHandler = null;

    public LikeAnimView(Context context) {
        super(context);
        init();
    }

    public LikeAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressWarnings("unused")
    public LikeAnimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        HandlerThread drawThread = new HandlerThread("LikeAnim--DrawThread");
        drawThread.start();
        mDrawHandler = new DrawHandler(drawThread.getLooper(), this::getHolder);
        getHolder().addCallback(this);
        setOnClickListener(v -> mDrawHandler.addHeart());
    }

    //手指轨迹
    //    @Override
    //    public boolean onTouchEvent(MotionEvent event) {
    //        mDrawHandler.addPoint(event.getX(), event.getY());
    //        getParent().requestDisallowInterceptTouchEvent(true);
    //        return true;
    //    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawHandler.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mDrawHandler.stop();
    }

    private static final HashMap<String, Integer> sColorNameMap;

    static {
        sColorNameMap = new HashMap<>();
        sColorNameMap.put("black", BLACK);
        sColorNameMap.put("darkgray", DKGRAY);
        sColorNameMap.put("gray", GRAY);
        sColorNameMap.put("lightgray", LTGRAY);
        sColorNameMap.put("white", WHITE);
        sColorNameMap.put("red", RED);
        sColorNameMap.put("green", GREEN);
        sColorNameMap.put("blue", BLUE);
        sColorNameMap.put("yellow", YELLOW);
        sColorNameMap.put("cyan", CYAN);
        sColorNameMap.put("magenta", MAGENTA);
        sColorNameMap.put("aqua", 0xFF00FFFF);
        sColorNameMap.put("fuchsia", 0xFFFF00FF);
        sColorNameMap.put("darkgrey", DKGRAY);
        sColorNameMap.put("grey", GRAY);
        sColorNameMap.put("lightgrey", LTGRAY);
        sColorNameMap.put("lime", 0xFF00FF00);
        sColorNameMap.put("maroon", 0xFF800000);
        sColorNameMap.put("navy", 0xFF000080);
        sColorNameMap.put("olive", 0xFF808000);
        sColorNameMap.put("purple", 0xFF800080);
        sColorNameMap.put("silver", 0xFFC0C0C0);
        sColorNameMap.put("teal", 0xFF008080);
    }

    @SuppressWarnings("WeakerAccess")
    static class BezierEvaluator {
        private PointF[] cp = new PointF[4];
        static Random random = new Random();

        private long startTime;
        private long duration = 5 * 1000;
        private long endTime;

        public BezierEvaluator(int width, int height) {

            startTime = SystemClock.uptimeMillis();
            endTime = startTime + duration;

            cp[0] = new PointF(width / 2, height);
            cp[1] = new PointF(random.nextInt(width), height - random.nextInt(height / 4));
            cp[2] = new PointF(random.nextInt(width), random.nextInt(height / 4));
            cp[3] = new PointF(width / 2, 0);
        }

        boolean isValidate() {
            return SystemClock.uptimeMillis() < endTime;
        }

        PointF evaluate() {
            return evaluate((SystemClock.uptimeMillis() - startTime) * 1.0f / duration);
        }

        PointF evaluate(float fraction) {
            return pointOnCubicBezier(cp, fraction);
        }

        /*
         * cp 在此是四个元素的数组: cp[0] 为起点，或上图中的 P0 cp[1] 为第一控制点，或上图中的 P1 cp[2]
         * 为第二控制点，或上图中的 P2 cp[3] 为结束点，或上图中的 P3 t 为参数值，0 <= t <= 1
         */
        private PointF pointOnCubicBezier(PointF[] cp, float t) {
            float ax;
            float bx;
            float cx;
            float ay;
            float by;
            float cy;
            float tSquared;
            float tCubed;
            PointF result = new PointF();
            /* 计算多项式系数 */
            cx = 3.0f * (cp[1].x - cp[0].x);
            bx = 3.0f * (cp[2].x - cp[1].x) - cx;
            ax = cp[3].x - cp[0].x - cx - bx;
            cy = 3.0f * (cp[1].y - cp[0].y);
            by = 3.0f * (cp[2].y - cp[1].y) - cy;
            ay = cp[3].y - cp[0].y - cy - by;
            /* 计算t位置的点值 */
            tSquared = t * t;
            tCubed = tSquared * t;
            result.x = (ax * tCubed) + (bx * tSquared) + (cx * t) + cp[0].x;
            result.y = (ay * tCubed) + (by * tSquared) + (cy * t) + cp[0].y;
            return result;
        }
    }


    class DrawHandler extends Handler implements Choreographer.FrameCallback {

        private final Supplier<SurfaceHolder> holderSupplier;
        private volatile boolean isStart = false;

        private Paint mPaint = new Paint();
        private HashMap<PointF, Integer> mPoints = new HashMap<>();
        private Random mColorRandom = new Random();
        private List<Integer> colors = new ArrayList<>();

        private HashMap<BezierEvaluator, Integer> evaluators = new HashMap<>();

        DrawHandler(Looper looper, Supplier<SurfaceHolder> supplier) {
            super(looper);
            this.holderSupplier = supplier;
            mPaint.setStrokeWidth(10);
            colors.addAll(sColorNameMap.values());
        }

        @Override
        public void doFrame(long frameTimeNanos) {
            if (isStart) {
                doDraw();
                Choreographer.getInstance().postFrameCallback(DrawHandler.this);
            }
        }

        private void doDraw() {
            SurfaceHolder surfaceHolder = holderSupplier.get();
            if (surfaceHolder != null) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(WHITE);
                    drawPoints(canvas);
                    drawHearts(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void drawHearts(Canvas canvas) {

            List<BezierEvaluator> invalidates = new ArrayList<>();

            evaluators.keySet().forEach(bezierEvaluator -> {
                if (bezierEvaluator.isValidate()) {
                    PointF pointF = bezierEvaluator.evaluate();
                    mPaint.setColor(evaluators.get(bezierEvaluator));
                    canvas.drawCircle(pointF.x, pointF.y, 20, mPaint);
                }else {
                    invalidates.add(bezierEvaluator);
                }
            });

            invalidates.forEach(bezierEvaluator -> evaluators.remove(bezierEvaluator));
        }

        private void drawPoints(Canvas canvas) {
            mPoints.keySet().forEach(pointF -> {
                mPaint.setColor(mPoints.get(pointF));
                canvas.drawCircle(pointF.x, pointF.y, 20, mPaint);
            });
        }

        public void start() {
            isStart = true;
            post(() -> Choreographer.getInstance().postFrameCallback(DrawHandler.this));
        }

        void stop() {
            isStart = false;
            removeCallbacksAndMessages(null);
        }

        //绘制手指轨迹
        @SuppressWarnings("unused")
        void addPoint(float x, float y) {
            post(() -> {
                PointF pointF = new PointF(x, y);
                mPoints.put(pointF, colors.get(mColorRandom.nextInt(colors.size())));
            });
        }


        void addHeart() {
            post(() -> {
                BezierEvaluator bezierEvaluator = new BezierEvaluator(dipToPx(300), dipToPx(300));
                evaluators.put(bezierEvaluator, colors.get(mColorRandom.nextInt(colors.size())));
            });
        }

    }

    private int dipToPx(int dip) {
        final DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int) (displayMetrics.density * dip + 0.5f);
    }

}
