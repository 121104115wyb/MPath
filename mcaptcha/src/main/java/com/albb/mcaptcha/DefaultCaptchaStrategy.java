package com.albb.mcaptcha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Random;

/**
 * 默认CaptchaStrategy
 * Created by luozhanming on 2018/1/19.
 */

public class DefaultCaptchaStrategy extends CaptchaStrategy {
    Path mCaptchaPath;

    public DefaultCaptchaStrategy(Context ctx) {
        super(ctx);
    }

    //定义拼图缺块的形状
    @Override
    public Path getBlockShape(int blockSize) {
        createCaptchaPath(blockSize);
        return mCaptchaPath;
    }

    //生成验证码Path
    private void createCaptchaPath(int blockSize) {
        //原本打算随机生成gap，后来发现 宽度/3 效果比较好，
//        int gap = mRandom.nextInt(mCaptchaWidth / 2);
        mCaptchaPath = new Path();
        Random mRandom = new Random();
        int gap = blockSize / 5;
        int mCaptchaWidth = 3 * gap;
        int mCaptchaHeight = 3 * gap;
        Log.d("DefaultCaptchaStrategy", "createCaptchaPath: ------blockSize:" + blockSize);

        //随机生成验证码阴影左上角 x y 点，
        int mCaptchaX = gap;
        int mCaptchaY = gap;
//
        mCaptchaPath.reset();
        mCaptchaPath.lineTo(gap, gap);

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
//
//
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


    /**
     * 根据整张拼图的宽高和拼图缺块大小定义拼图缺块的位置
     *
     * @param width  picture width
     * @param height picture height
     * @return position info of the block
     */
    @Override
    public @NonNull
    PositionInfo getBlockPostionInfo(int width, int height, int blockSize) {
        Random random = new Random();
        int left = random.nextInt(width - blockSize + 1);
        //Avoid robot frequently and quickly click the start point to access the captcha.
        if (left < blockSize) {
            left = blockSize;
        }
        int top = random.nextInt(height - blockSize + 1);
        if (top < 0) {
            top = 0;
        }

        Log.d("DefaultCaptchaStrategy", "getBlockPostionInfo: ----left:" + left + "----top:" + top);
        return new PositionInfo(left, top);
    }

    /**
     * 定义滑块图片的位置信息(只有设置为无滑动条模式有用并建议重写)
     *
     * @param width  picture width
     * @param height picture height
     * @return position info of the block
     */
    @Override
    public @NonNull
    PositionInfo getPositionInfoForSwipeBlock(int width, int height, int blockSize) {
        Random random = new Random();
        int left = random.nextInt(width - blockSize + 1);
        int top = random.nextInt(height - blockSize + 1);
        if (top < 0) {
            top = 0;
        }
        return new PositionInfo(left, top);
    }

    /**
     * 定义拼图缺失部分阴影的Paint
     */
    @Override
    public Paint getBlockShadowPaint() {
        Paint shadowPaint = new Paint();
        shadowPaint.setColor(Color.parseColor("#000000"));
        shadowPaint.setAlpha(165);
        return shadowPaint;
    }

    /**
     * 获得拼图缺块图片的Paint
     */
    @Override
    public Paint getBlockBitmapPaint() {
        Paint paint = new Paint();
        return paint;
    }

    /**
     * 装饰滑块图片，在绘制图片后执行，即绘制滑块前景
     *
     * @params canvas
     * @params shape   缺块的形状
     */
    @Override
    public void decoreateSwipeBlockBitmap(Canvas canvas, Path shape) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#00000000"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
//        paint.setPathEffect(new DashPathEffect(new float[]{20,20},10));
        Path path = new Path(shape);
        canvas.drawPath(path, paint);
    }


}
