package com.example.mobileshop.person;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/*
SurfaceView是视图类View的子类，且实现了Parcelable接口且实现了Parcelable接口，
其中内嵌了一个专门用于绘制的Surface，SurfaceView可以控制这个Surface的格式和尺寸，
以及Surface的绘制位置。可以理解为Surface就是管理数据的地方，SurfaceView就是展示数据的地方。
SurfaceHolder是一个接口，类似于一个surace的监听器。通过下面三个回调方法监听Surface的创建、销毁或者改变。
    SurfaceView中调用getHolder方法，可以获得当前SurfaceView中的surface对应的SurfaceHolder，SurfaceHolder中重要的方法有：
    1： abstract  void addCallback（SurfaceHolder.Callback callback );为SurfaceHolder添加一个SurfaceHolder.Callback回调接口。
   2:  abstract  Canvas lockCanvas() ;获取Surface中的Canvas对象，并锁定之。所得到的Canvas对象。
    3：abstract  void unlockCanvasAndPost(Canvas canvas);当修改Surface中的数据完成后，释放同步锁，并提交改变，然后将新的数据进行展示。
SurfaceHolder.Callback是SurfaceHolder接口内部的静态子接口，SurfaceHolder.Callback中定义了三个接口方法：
   1：public void sufaceChanged(SurfaceHolder holder,int format,int width,int height){}//Surface的大小发生改变时调用。
   2： public void surfaceCreated(SurfaceHolder holder){}//Surface创建时激发，一般在这里调用画面的线程。
   3： public void surfaceDestroyed(SurfaceHolder holder){}//销毁时激发，一般在这里将画面的线程停止、释放。
 */
public class HeartView extends SurfaceView implements SurfaceHolder.Callback{

    SurfaceHolder surfaceHolder;
    int offsetX;
    int offsetY;
    private Garden garden;
    private int width;
    private int height;
    private Paint backgroundPaint;
    private boolean isDrawing = false;
    private Bitmap bm;
    private Canvas canvas;
    private int heartRadio = 1;

    public HeartView(Context context) {
        super(context);
        init();
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        garden = new Garden();
        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.rgb(0xff, 0xff, 0xe0));


    }

    ArrayList<Bloom> blooms = new ArrayList<>();

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (heartRadio * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-heartRadio * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));

        return new Point(offsetX + (int) x, offsetY + (int) y);
    }


    //绘制列表里所有的花朵
    private void drawHeart() {
        canvas.drawRect(0, 0, width, height, backgroundPaint);
        for (Bloom b : blooms) {
            b.draw(canvas);
        }
        Canvas c = surfaceHolder.lockCanvas();

        c.drawBitmap(bm, 0, 0, null);

        surfaceHolder.unlockCanvasAndPost(c);

    }

    public void reDraw() {
        blooms.clear();


        drawOnNewThread();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    //开启一个新线程绘制
    private void drawOnNewThread() {
        new Thread() {
            @Override
            public void run() {
                if (isDrawing) return;
                isDrawing = true;

                float angle = 10;
                while (true) {

                    Bloom bloom = getBloom(angle);
                    if (bloom != null) {
                        blooms.add(bloom);
                    }
                    if (angle >= 30) {
                        break;
                    } else {
                        angle += 0.2;
                    }
                    drawHeart();
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isDrawing = false;
            }
        }.start();
    }


    private Bloom getBloom(float angle) {

        Point p = getHeartPoint(angle);

        boolean draw = true;
        /**循环比较新的坐标位置是否可以创建花朵,
         * 为了防止花朵太密集
         * */
        for (int i = 0; i < blooms.size(); i++) {

            Bloom b = blooms.get(i);
            Point bp = b.getPoint();
            float distance = (float) Math.sqrt(Math.pow(p.x - bp.x, 2) + Math.pow(p.y - bp.y, 2));
            if (distance < Garden.Options.maxBloomRadius * 1.5) {
                draw = false;
                break;
            }
        }
        //如果位置间距满足要求，就在该位置创建花朵并将花朵放入列表
        if (draw) {
            AtomicReference<Bloom> bloom = new AtomicReference<>(garden.createRandomBloom(p.x, p.y));
            return bloom.get();
        }
        return null;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        this.width = width;
        this.height = height;
        //我的手机宽度像素是1080，发现参数设置为30比较合适，这里根据不同的宽度动态调整参数
        heartRadio = width * 30 / 1080;

        offsetX = width / 2;
        offsetY = height / 2 - 55;
        bm = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas = new Canvas(bm);
        drawOnNewThread();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
