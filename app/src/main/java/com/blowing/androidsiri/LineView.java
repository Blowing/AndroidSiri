package com.blowing.androidsiri;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wujie
 * on 2019/5/30/030.
 */
public class LineView extends View {

    private Paint paint;
    private int width;
    private int height;
    private float offset = 1;
    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context,  AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setDither(true);


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        startAni();
    }

    private void startAni() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatorValue = (float) animation.getAnimatedValue();
                offset = animatorValue;
                postInvalidate();
            }
        });
        animator.setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
       animator.start();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float period = 2.0f;// 区域内，正弦波的周期

        // 将绘图原点移动到区域中心
        int width = getWidth();
        int height = getHeight();
        float midWidth = width / 2.0f;
        float midHeight = height / 2.0f;
        canvas.translate(midWidth, midHeight);


        // 初始化画笔

        paint.setStyle(Paint.Style.STROKE);//空心效果
        paint.setAntiAlias(true);//抗锯齿
        int color1 = getResources().getColor(R.color.colorAccent);
        int color2 = getResources().getColor(R.color.colorPrimary);
        int color3 = getResources().getColor(R.color.speech_new_main_bg);
        LinearGradient linearGradient = new LinearGradient(0, 0, width,0,new int[] {color1, color2, color3}, null, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);


        // 初始化线条
        Path sinPath = new Path();
        int x = (int) midWidth;
        sinPath.moveTo(-midWidth*2, 0);
        sinPath.quadTo( -midWidth * 3 /4 -midWidth + offset * x , -200*offset, -midWidth/2+x * offset-midWidth , 0);
        sinPath.quadTo(-midWidth/2 +offset * x-midWidth, 300*offset, 0+offset * x-midWidth, 0);
        sinPath.quadTo(-midWidth / 4 +offset * x-midWidth, -300*offset, -midWidth / 2+offset * x-midWidth, 0);
        sinPath.quadTo(-midWidth *3/ 4 +offset * x-midWidth, 200*offset, -midWidth+offset * x -midWidth, 0);

        sinPath.quadTo( -midWidth * 3 /4 + offset * x , -200*offset, -midWidth/2+x * offset , 0);
        sinPath.quadTo(-midWidth/4 +offset * x, 300*offset, 0+offset * x, 0);
        sinPath.quadTo(midWidth / 4 +offset * x, -300*offset, midWidth / 2+offset * x, 0);
        sinPath.quadTo(midWidth *3/ 4 +offset * x, 200*offset, midWidth+offset * x , 0);

//        sinPath.quadTo( -midWidth * 3 /4 + offset , -200, -midWidth/2 + offset, 0);
//        sinPath.quadTo(-midWidth/4 +offset, 300, 0+offset, 0);
//        sinPath.quadTo(midWidth / 4 + offset, -300, midWidth / 2+offset, 0);
//        sinPath.quadTo(midWidth *3/ 4 +offset, 200, midWidth +offset, 0);


        // 计算线条
//        for (float x = -midWidth; x < midWidth; x++) {
//            double sine = Math.sin(2 * Math.PI * period * (x / width));//计算该点上的正弦值
//            float y = (float) (midHeight * sine);// 将正弦值限定到绘图区的高度上
//            sinPath.lineTo(x, y);
//        }

        canvas.drawPath(sinPath, paint);//绘制线条

//        canvas.restore();


    }
}

