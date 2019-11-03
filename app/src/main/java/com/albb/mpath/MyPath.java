package com.albb.mpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class MyPath extends View {

    Paint paint;
    Path mCaptchaPath;
    public MyPath(Context context) {
        super(context);
    }

    public MyPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float blockSize = 150f;
//        int gap = (int) (blockSize/5f);
//        Path path = new Path();
//        path.moveTo(0, gap);
//        path.rLineTo(blockSize/2.5f, 0);
//        path.rLineTo(0, -gap);
//        path.rLineTo(gap, 0);
//        path.rLineTo(0, gap);
//        path.rLineTo(2 * gap, 0);
//        path.rLineTo(0, 4 * gap);
//        path.rLineTo(-5 * gap, 0);
//        path.rLineTo(0, -1.5f * gap);
//        path.rLineTo(gap, 0);
//        path.rLineTo(0, -gap);
//        path.rLineTo(-gap, 0);



//        Path path = new Path();
////
//        path.moveTo(30,60);
//        path.rLineTo(90,0);
//        path.rMoveTo(60,0);
//
//        RectF rectF = new RectF(120, 60, 180, 120);
//        path.addArc(rectF,0,-180);
//
//        path.rLineTo(90,0);
//        path.rLineTo(0,90);
//
//        path.rMoveTo(0,60);
//        path.rLineTo(0,90);
//        path.rLineTo(-240,0);
//
//        path.moveTo(30,60);
//
//        path.rLineTo(0,90);
//
//        path.rMoveTo(0,60);
//
//        path.rLineTo(0,90);
//
//        path.rLineTo(0,-60);
//        path.rMoveTo(0,-60);
////        path.close();
//        canvas.drawPath(path,paint);

        createCaptchaPath();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mCaptchaPath, paint);

    }


    //生成验证码Path
    private void createCaptchaPath() {
        //原本打算随机生成gap，后来发现 宽度/3 效果比较好，
//        int gap = mRandom.nextInt(mCaptchaWidth / 2);
         mCaptchaPath = new Path();
        Random mRandom = new Random();
       int mCaptchaWidth = 120;
        int mCaptchaHeight = 120;
        int  gap = 120 / 3;

//        //随机生成验证码阴影左上角 x y 点，
//      int  mCaptchaX = mRandom.nextInt(mWidth - mCaptchaWidth - gap);
//       int mCaptchaY = mRandom.nextInt(mHeight - mCaptchaHeight - gap);

        //随机生成验证码阴影左上角 x y 点，
        int  mCaptchaX = 40;
        int mCaptchaY = 40;

        mCaptchaPath.reset();
        mCaptchaPath.lineTo(0, 0);

        //从左上角开始 绘制一个不规则的阴影
        mCaptchaPath.moveTo(mCaptchaX, mCaptchaY);//左上角
        mCaptchaPath.lineTo(mCaptchaX + gap, mCaptchaY);
        //draw一个随机凹凸的圆
        drawPartCircle(new PointF(mCaptchaX + gap, mCaptchaY),
                new PointF(mCaptchaX + gap * 2, mCaptchaY),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY);//右上角
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + gap);
        //draw一个随机凹凸的圆
        drawPartCircle(new PointF(mCaptchaX + mCaptchaWidth, mCaptchaY + gap),
                new PointF(mCaptchaX + mCaptchaWidth, mCaptchaY + gap * 2),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth, mCaptchaY + mCaptchaHeight);//右下角
        mCaptchaPath.lineTo(mCaptchaX + mCaptchaWidth - gap, mCaptchaY + mCaptchaHeight);
        //draw一个随机凹凸的圆
        drawPartCircle(new PointF(mCaptchaX + mCaptchaWidth - gap, mCaptchaY + mCaptchaHeight),
                new PointF(mCaptchaX + mCaptchaWidth - gap * 2, mCaptchaY + mCaptchaHeight),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight);//左下角
        mCaptchaPath.lineTo(mCaptchaX, mCaptchaY + mCaptchaHeight - gap);
        //draw一个随机凹凸的圆
        drawPartCircle(new PointF(mCaptchaX, mCaptchaY + mCaptchaHeight - gap),
                new PointF(mCaptchaX, mCaptchaY + mCaptchaHeight - gap * 2),
                mCaptchaPath, mRandom.nextBoolean());


        mCaptchaPath.close();
    }

    /**
     * 传入起点、终点 坐标、凹凸和Path。
     * 会自动绘制凹凸的半圆弧
     *
     * @param start 起点坐标
     * @param end   终点坐标
     * @param path  半圆会绘制在这个path上
     * @param outer 是否凸半圆
     */
    public static void drawPartCircle(PointF start, PointF end, Path path, boolean outer) {
        float c = 0.551915024494f;
        //中点
        PointF middle = new PointF(start.x + (end.x - start.x) / 2, start.y + (end.y - start.y) / 2);
        //半径
        float r1 = (float) Math.sqrt(Math.pow((middle.x - start.x), 2) + Math.pow((middle.y - start.y), 2));
        //gap值
        float gap1 = r1 * c;

        if (start.x == end.x) {
            //绘制竖直方向的

            //是否是从上到下
            boolean topToBottom = end.y - start.y > 0 ? true : false;
            //以下是我写出了所有的计算公式后推的，不要问我过程，只可意会。
            int flag;//旋转系数
            if (topToBottom) {
                flag = 1;
            } else {
                flag = -1;
            }
            if (outer) {
                //凸的 两个半圆
                path.cubicTo(start.x + gap1 * flag, start.y,
                        middle.x + r1 * flag, middle.y - gap1 * flag,
                        middle.x + r1 * flag, middle.y);
                path.cubicTo(middle.x + r1 * flag, middle.y + gap1 * flag,
                        end.x + gap1 * flag, end.y,
                        end.x, end.y);
            } else {
                //凹的 两个半圆
                path.cubicTo(start.x - gap1 * flag, start.y,
                        middle.x - r1 * flag, middle.y - gap1 * flag,
                        middle.x - r1 * flag, middle.y);
                path.cubicTo(middle.x - r1 * flag, middle.y + gap1 * flag,
                        end.x - gap1 * flag, end.y,
                        end.x, end.y);
            }
        } else {
            //绘制水平方向的

            //是否是从左到右
            boolean leftToRight = end.x - start.x > 0 ? true : false;
            //以下是我写出了所有的计算公式后推的，不要问我过程，只可意会。
            int flag;//旋转系数
            if (leftToRight) {
                flag = 1;
            } else {
                flag = -1;
            }
            if (outer) {
                //凸 两个半圆
                path.cubicTo(start.x, start.y - gap1 * flag,
                        middle.x - gap1 * flag, middle.y - r1 * flag,
                        middle.x, middle.y - r1 * flag);
                path.cubicTo(middle.x + gap1 * flag, middle.y - r1 * flag,
                        end.x, end.y - gap1 * flag,
                        end.x, end.y);
            } else {
                //凹 两个半圆
                path.cubicTo(start.x, start.y + gap1 * flag,
                        middle.x - gap1 * flag, middle.y + r1 * flag,
                        middle.x, middle.y + r1 * flag);
                path.cubicTo(middle.x + gap1 * flag, middle.y + r1 * flag,
                        end.x, end.y + gap1 * flag,
                        end.x, end.y);
            }


/*
            没推导之前的公式在这里
            if (start.x < end.x) {
                if (outer) {
                    //上左半圆 顺时针
                    path.cubicTo(start.x, start.y - gap1,
                            middle.x - gap1, middle.y - r1,
                            middle.x, middle.y - r1);

                    //上右半圆:顺时针
                    path.cubicTo(middle.x + gap1, middle.y - r1,
                            end.x, end.y - gap1,
                            end.x, end.y);
                } else {
                    //下左半圆 逆时针
                    path.cubicTo(start.x, start.y + gap1,
                            middle.x - gap1, middle.y + r1,
                            middle.x, middle.y + r1);

                    //下右半圆 逆时针
                    path.cubicTo(middle.x + gap1, middle.y + r1,
                            end.x, end.y + gap1,
                            end.x, end.y);
                }
            } else {
                if (outer) {
                    //下右半圆 顺时针
                    path.cubicTo(start.x, start.y + gap1,
                            middle.x + gap1, middle.y + r1,
                            middle.x, middle.y + r1);
                    //下左半圆 顺时针
                    path.cubicTo(middle.x - gap1, middle.y + r1,
                            end.x, end.y + gap1,
                            end.x, end.y);
                }
            }*/
        }
    }



}
