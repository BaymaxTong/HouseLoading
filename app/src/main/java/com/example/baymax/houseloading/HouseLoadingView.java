package com.example.baymax.houseloading;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by baymax on 2016/4/19.
 */
public class HouseLoadingView extends View {

    public HouseLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResources = getResources();
    }

    private Paint mPaint;
    // 淡白色
    private static final int WHITE_COLOR = 0xffFFFFFF;
    // 橙色
    private static final int ORANGE_COLOR = 0xffFFCC66;
    //棕色
    private static final int BROWN_COLOR = 0xff604B46;
    //黑色
    private static final int BLACK_COLOR = 0xff000000;
    //蓝色
    private static final int BLUE_COLOR = 0xff78D4FF;

    private int mTotalWidth, mTotalHeight;
    private Bitmap firstHouseBitmap,secondHouseBitmap,thirdHouseBitmap,lastHouseBitmap;
    private Resources mResources;
    private int firstHouseWidth,firstHouseHeight;
    private int secondHouseWidth,secondHouseHeight;
    private int thirdHouseWidth,thirdHouseHeight;
    private int lastHouseWidth,lastHouseHeight;
    private int mProgressWidth;

    // 当前进度
    private int mProgress;
    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 当前所在的绘制的进度条的位置
    private int mCurrentProgressPosition;

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
        } else {
            mPaint.reset();
        }
        mPaint.setAntiAlias(true);//边缘无锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();            //初始化画笔
        drawBackground(canvas); //绘制背景
        drawLoadingText(canvas);//绘制文字
        drawFirstHouse(canvas); //绘制第一个房子
        drawProgress(canvas);   //绘制进度框
        postInvalidate();//刷新
    }

    /**
     * 波浪显示
     * @param canvas
     * @param width    波浪的宽度
     * @param length   波浪的高度
     * @param left     波浪的左起点
     * @param top      波浪的上顶点
     */
    private void setPath(Canvas canvas,int width,int length,int left,int top){
        Path path = new Path();
        path.reset();
        int x,y;
        int a = 12;//振幅
        for (int i = mProgress; i < width + mProgress ; i++) {
            x = left + i- mProgress;
            y = (int) (a * Math.sin(i * Math.PI / 50) + length + top);
            if (i == mProgress) {
                path.moveTo(x, y);
            }
            path.quadTo(x, y, x + 1, y);
        }
        path.lineTo(left + width, top);
        path.lineTo(left, top);
        path.close();
        Paint wavePaint = new Paint();
        wavePaint.setStyle(Paint.Style.FILL);
        wavePaint.setAntiAlias(true);
        wavePaint.setColor(ORANGE_COLOR);
        canvas.drawPath(path, wavePaint);
    }

    private void drawTree(Canvas canvas) {
        initPaint();
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        Bitmap treeBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.tree)).getBitmap();
        int treeWidth = treeBitmap.getWidth();
        int treeHeight = treeBitmap.getHeight();
        Rect mSrcRect = new Rect(0, 0, treeWidth, treeHeight);
        Rect mDestRect = new Rect(750, mTotalHeight/2 -60, 890, mTotalHeight/2 + 100);
        canvas.drawBitmap(treeBitmap, mSrcRect, mDestRect, mPaint);
    }

    private void drawGrass(Canvas canvas) {
        initPaint();
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        Bitmap grassBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.grass)).getBitmap();
        int grassWidth = grassBitmap.getWidth();
        int grassHeight = grassBitmap.getHeight();
        Rect mSrcRect = new Rect(0, 0, grassWidth, grassHeight);
        Rect mDestRect = new Rect(465, mTotalHeight/2 + 40, 570, mTotalHeight/2 + 100);
        canvas.drawBitmap(grassBitmap, mSrcRect, mDestRect, mPaint);
    }

    private void drawLastHouse(Canvas canvas) {
        if(mProgress < 60){
            // do nothing
        } else if(mProgress <= 99){
            initPaint();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            lastHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.last)).getBitmap();
            lastHouseWidth = lastHouseBitmap.getWidth();
            lastHouseHeight = lastHouseBitmap.getHeight();
            Rect mSrcRect = new Rect(0, 0, lastHouseWidth, lastHouseHeight);
            Rect mDestRect = new Rect(675, mTotalHeight/2 - 70, 800, mTotalHeight/2 + 100);
            canvas.drawBitmap(lastHouseBitmap, mSrcRect, mDestRect, mPaint);
            setPath(canvas, 125, 150 - 77 * (mProgress - 60)/20, 675, mTotalHeight / 2 - 70);
        }else{
            initPaint();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            lastHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.last)).getBitmap();
            lastHouseWidth = lastHouseBitmap.getWidth();
            lastHouseHeight = lastHouseBitmap.getHeight();
            Rect mSrcRect = new Rect(0, 0, lastHouseWidth, lastHouseHeight);
            Rect mDestRect = new Rect(675, mTotalHeight/2 - 70, 800, mTotalHeight/2 + 100);
            canvas.drawBitmap(lastHouseBitmap, mSrcRect, mDestRect, mPaint);
        }
    }

    private void drawThirdHouse(Canvas canvas) {
        if(mProgress < 40){
            // do nothing
        } else if(mProgress < 90){
            initPaint();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            thirdHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.third)).getBitmap();
            thirdHouseWidth = thirdHouseBitmap.getWidth();
            thirdHouseHeight = thirdHouseBitmap.getHeight();
            Rect mSrcRect = new Rect(0, 0, thirdHouseWidth, thirdHouseHeight);
            Rect mDestRect = new Rect(525, mTotalHeight/2 - 150, 670, mTotalHeight/2 + 100);
            canvas.drawBitmap(thirdHouseBitmap, mSrcRect, mDestRect, mPaint);
            setPath(canvas, 145, 230 - 23 * (mProgress - 40)/5, 525, mTotalHeight / 2 - 150);
        }else{
            initPaint();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            thirdHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.third)).getBitmap();
            thirdHouseWidth = thirdHouseBitmap.getWidth();
            thirdHouseHeight = thirdHouseBitmap.getHeight();
            Rect mSrcRect = new Rect(0, 0, thirdHouseWidth, thirdHouseHeight);
            Rect mDestRect = new Rect(525, mTotalHeight/2 - 150, 670, mTotalHeight/2 + 100);
            canvas.drawBitmap(thirdHouseBitmap, mSrcRect, mDestRect, mPaint);
        }
    }

    private void drawSecondHouse(Canvas canvas) {
        if(mProgress < 20){
            // do nothing
        } else if(mProgress < 70){
            initPaint();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            secondHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.second)).getBitmap();
            secondHouseWidth = secondHouseBitmap.getWidth();
            secondHouseHeight = secondHouseBitmap.getHeight();
            Rect mSrcRect = new Rect(0, 0, firstHouseWidth, secondHouseHeight);
            Rect mDestRect = new Rect(375, mTotalHeight/2 - 150, 525, mTotalHeight/2 + 100);
            canvas.drawBitmap(secondHouseBitmap, mSrcRect, mDestRect, mPaint);
            setPath(canvas, 150, 230 - 23 * (mProgress-20)/5, 375, mTotalHeight / 2 - 150);
        }else{
            initPaint();
            mPaint.setDither(true);
            mPaint.setFilterBitmap(true);
            secondHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.second)).getBitmap();
            secondHouseWidth = secondHouseBitmap.getWidth();
            secondHouseHeight = secondHouseBitmap.getHeight();
            Rect mSrcRect = new Rect(0, 0, firstHouseWidth, secondHouseHeight);
            Rect mDestRect = new Rect(375, mTotalHeight/2 - 150, 525, mTotalHeight/2 + 100);
            canvas.drawBitmap(secondHouseBitmap, mSrcRect, mDestRect, mPaint);
        }
    }

    private void drawFirstHouse(Canvas canvas) {
        initPaint();
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        firstHouseBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.first)).getBitmap();
        firstHouseWidth = firstHouseBitmap.getWidth();
        firstHouseHeight = firstHouseBitmap.getHeight();
        Rect mSrcRect = new Rect(0, 0, firstHouseWidth, firstHouseHeight);
        Rect mDestRect = new Rect(220, mTotalHeight/2 - 150, 370, mTotalHeight/2 + 100);
        canvas.drawBitmap(firstHouseBitmap, mSrcRect, mDestRect, mPaint);
    }

    private void drawCloud(Canvas canvas) {
        initPaint();
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        Bitmap cloudBitmap = ((BitmapDrawable) mResources.getDrawable(R.mipmap.cloud)).getBitmap();
        int cloudWidth = cloudBitmap.getWidth();
        int cloudHeight = cloudBitmap.getHeight();
        Rect mSrcRect = new Rect(0, 0, cloudWidth, cloudHeight);
        int left = 525;
        int top = mTotalHeight/2 - 160;
        int right = 615;
        int bottom = mTotalHeight/2 - 90;
        int shift = 417 * mProgress / TOTAL_PROGRESS;
        if(mProgress >= 90){
            int shiftY = 400 * (mProgress-90) / TOTAL_PROGRESS;
            Rect mDestRect = new Rect(left+250-shiftY,top - 30,right+250-shiftY,bottom - 30);
            canvas.drawBitmap(cloudBitmap, mSrcRect, mDestRect, mPaint);
        }else if(mProgress >= 60){
            int shiftY = 100 * (mProgress-60) / TOTAL_PROGRESS;
            Rect mDestRect = new Rect(left+250,top - shiftY,right+250,bottom - shiftY);
            canvas.drawBitmap(cloudBitmap, mSrcRect, mDestRect, mPaint);
        }else{
            Rect mDestRect = new Rect(left+shift,top,right+shift,bottom);
            canvas.drawBitmap(cloudBitmap, mSrcRect, mDestRect, mPaint);
        }
    }

    private void drawLoadingText(Canvas canvas) {
        initPaint();
        mPaint.setColor(BLACK_COLOR);
        mPaint.setTextSize(60);
        String str = "loading...";
        canvas.drawText(str, mTotalWidth / 2 - 80, mTotalHeight / 2 + 280, mPaint);
    }

    private void drawProgress(Canvas canvas) {
        if (mProgress > TOTAL_PROGRESS) {
            mProgress = 0;
        }
        mProgressWidth = mTotalWidth - 400;
        // 当前进度算出进度条的位置
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS;
        drawChimney(canvas);
        drawCloud(canvas);
        drawSecondHouse(canvas); //绘制第二个房子
        drawThirdHouse(canvas); //绘制第三个房子
        drawLastHouse(canvas);  //绘制最后一个房子
        if(mCurrentProgressPosition <= 30){
            drawLeft(canvas, mCurrentProgressPosition);
        }else if(mCurrentProgressPosition >= mTotalWidth - 430){
            //draw left
            drawLeft(canvas,30);
            //draw center
            drawCenter(canvas,mProgressWidth - 30);
            //draw right
            drawRight(canvas, mCurrentProgressPosition);
        }else{
            //draw left
            drawLeft(canvas,30);
            //draw center
            drawCenter(canvas,mCurrentProgressPosition);
        }
        drawTree(canvas);       //绘制树
        drawGrass(canvas);      //绘制草
        drawRoundRect(canvas);  //进度框
    }

    private void drawChimney(Canvas canvas) {
        //绘制第一个烟囱
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(WHITE_COLOR);
        mPaint.setStrokeWidth(8);
        int left = 335;
        int top = mTotalHeight/2 - 140;
        int right = 350;
        int bottom = mTotalHeight / 2 - 130;
        int shift = 60 * mProgress / TOTAL_PROGRESS;
        RectF rectF = new RectF(left + shift,top - 4*shift/3,right + 3*shift/2,bottom - shift);
        canvas.drawOval(rectF, mPaint);
        //绘制第二个烟囱
        int left2,top2,right2,bottom2,shift2;
        if(mProgress >= 58){
            left2 = 335;
            top2 = mTotalHeight/2 - 140;
            right2 = 350;
            bottom2 = mTotalHeight / 2 - 130;
            shift2 = 60 * (mProgress-58) / TOTAL_PROGRESS;
        }else{
            left2 = 0;
            top2 = 0;
            right2 = 0;
            bottom2 = 0;
            shift2 = 0;
        }
        RectF rectSecond = new RectF(left2 + shift2,top2 - 4*shift2/3,right2 + 3*shift2/2,bottom2 - shift2);
        canvas.drawOval(rectSecond, mPaint);
    }

    private void drawRight(Canvas canvas,int mCurrentProgressPosition) {
        initPaint();
        mPaint.setColor(BLUE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        int height = (int) Math.sqrt(900-(mCurrentProgressPosition - mProgressWidth + 30)*(mCurrentProgressPosition - mProgressWidth + 30));
        //上圆弧
        RectF rectUP = new RectF();
        rectUP.left = 200 + mProgressWidth - 60;
        rectUP.top = mTotalHeight/2 + 105;
        rectUP.right = mProgressWidth + 200;
        rectUP.bottom = mTotalHeight/2 + 155;
        canvas.drawArc(rectUP, 270, 90, true, mPaint);
        //中部
        RectF mRect = new RectF();
        mRect.left = 200 + mProgressWidth - 30;
        mRect.top = mTotalHeight/2 + 130;
        mRect.right = mCurrentProgressPosition + 200;
        mRect.bottom = mTotalHeight/2 + 170;
        canvas.drawRect(mRect, mPaint);
        //下圆弧
        RectF rectDown = new RectF();
        rectDown.left = 200 + mProgressWidth - 60;
        rectDown.top = mTotalHeight/2 + 145;
        rectDown.right = mProgressWidth + 200;
        rectDown.bottom = mTotalHeight/2 + 195;
        canvas.drawArc(rectDown, 0, 90, true, mPaint);

        mPaint.setColor(WHITE_COLOR);
        int shiftX = mProgressWidth - mCurrentProgressPosition;
        //上圆弧
        RectF rectUp = new RectF();
        rectUp.left = 200 + mCurrentProgressPosition - shiftX;
        rectUp.top = mTotalHeight/2 + 135 - height;
        rectUp.right = mProgressWidth + 200;
        rectUp.bottom = mTotalHeight/2 + 135 + height;
        canvas.drawArc(rectUp, 270, 90, true, mPaint);
        //下圆弧
        RectF rectRDown = new RectF();
        rectRDown.left = 200 + mCurrentProgressPosition - shiftX;
        rectRDown.top = mTotalHeight/2 + 175 - height;
        rectRDown.right = mProgressWidth + 200;
        rectRDown.bottom = mTotalHeight/2 + 165 + height;
        canvas.drawArc(rectRDown, 0, 90, true, mPaint);
    }

    private void drawCenter(Canvas canvas,int mCurrentProgressPosition) {
        initPaint();
        mPaint.setColor(BLUE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        RectF mRect = new RectF();
        mRect.left = 230;
        mRect.top = mTotalHeight/2 + 105;
        mRect.right = mCurrentProgressPosition + 200;
        mRect.bottom = mTotalHeight/2 + 195;
        canvas.drawRect(mRect, mPaint);
    }

    private void drawLeft(Canvas canvas,int mCurrentProgressPosition) {
        initPaint();
        mPaint.setColor(BLUE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        int height = (int) Math.sqrt(900-(30-mCurrentProgressPosition)*(30-mCurrentProgressPosition));
        //上圆弧
        RectF rectUP = new RectF();
        rectUP.left = 200;
        rectUP.top = mTotalHeight/2 + 135 - height;
        rectUP.right = 2 * mCurrentProgressPosition + 200;
        rectUP.bottom = mTotalHeight/2 + 125 + height;
        canvas.drawArc(rectUP, 180, 90, true, mPaint);
        //中部
        RectF mRect = new RectF();
        mRect.left = 200;
        mRect.top = mTotalHeight/2 + 130;
        mRect.right = mCurrentProgressPosition + 200;
        mRect.bottom = mTotalHeight/2 + 170;
        canvas.drawRect(mRect, mPaint);
        //下圆弧
        RectF rectDown = new RectF();
        rectDown.left = 200;
        rectDown.top = mTotalHeight/2 + 175 - height;
        rectDown.right = 2 * mCurrentProgressPosition + 200;
        rectDown.bottom = mTotalHeight/2 + 165 + height;
        canvas.drawArc(rectDown, 90, 90, true, mPaint);
    }

    private void drawBackground(Canvas canvas) {
        //背景
        initPaint();
        mPaint.setColor(ORANGE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        RectF backgroundRect = new RectF(0,0,mTotalWidth,mTotalHeight);
        canvas.drawRect(backgroundRect, mPaint);
        //进度框颜色
        initPaint();
        mPaint.setColor(WHITE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        RectF progressRect = new RectF();
        progressRect.left = 200;
        progressRect.top = mTotalHeight/2 + 100;
        progressRect.right = mTotalWidth - 200;
        progressRect.bottom = mTotalHeight/2 + 200;
        canvas.drawRoundRect(progressRect, 30, 30, mPaint);
        drawRoundRect(canvas);//进度框
    }

    private void drawRoundRect(Canvas canvas) {
        //进度框
        initPaint();
        mPaint.setColor(BROWN_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);          //画笔粗细设置
        RectF progressRect = new RectF();
        progressRect.left = 200;
        progressRect.top = mTotalHeight/2 + 100;
        progressRect.right = mTotalWidth - 200;
        progressRect.bottom = mTotalHeight/2 + 200;
        canvas.drawRoundRect(progressRect, 30, 30, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
    }

    /**
     * 设置进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }


}
