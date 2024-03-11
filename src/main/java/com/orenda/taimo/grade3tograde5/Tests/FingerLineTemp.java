package com.orenda.taimo.grade3tograde5.Tests;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.orenda.taimo.grade3tograde5.Models.LineModel;

import java.util.ArrayList;

import static com.orenda.taimo.grade3tograde5.Tests.MatchTextToText.canvasTotalX;
import static com.orenda.taimo.grade3tograde5.Tests.MatchTextToText.canvasTotalY;
import static com.orenda.taimo.grade3tograde5.Tests.MatchTextToText.endY;

public class FingerLineTemp extends View {
    public static Paint mPaint;
    float strokeWidth = 5;
    Canvas canvas;
    int count = 0;

    public static ArrayList<LineModel> line = null;

    public FingerLineTemp(Context context) {
        this(context, null);
    }

    public FingerLineTemp(Context context, AttributeSet attrs) {
        super(context, attrs);
        line = new ArrayList<LineModel>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        //canvas.drawLine(new LineModel(mPaint,startX,startY,endX,endY));
        canvas.drawLine(MatchTextToText.startX, MatchTextToText.startY, MatchTextToText.endX, endY, mPaint);
        for (int i = 0; i < line.size(); i++) {
            canvas.drawLine(line.get(i).getStartX(), line.get(i).getStartY(), line.get(i).getEndX(), line.get(i).getEndY(), line.get(i).getmPaint());
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        LineModel l = new LineModel();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(Color.WHITE);
        l.setmPaint(mPaint);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MatchTextToText.startX = event.getX();
                MatchTextToText.startY = event.getY();
                // Set the end to prevent initial jump (like on the demo recording)
                MatchTextToText.endX = event.getX();
                endY = event.getY();
                Log.wtf("-this", " StartX : " + MatchTextToText.startX + " || StartY : " + MatchTextToText.startY + "  |||||  EndX : " + MatchTextToText.endX + " EndY : " + endY);

                l.setmPaint(mPaint);
                l.setStartX(MatchTextToText.startX);
                l.setStartY(MatchTextToText.startY);
                l.setEndX(MatchTextToText.endX);
                l.setEndY(endY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                MatchTextToText.endX = event.getX();
                endY = event.getY();
                l.setEndX(MatchTextToText.endX);
                l.setEndY(endY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                MatchTextToText.endX = event.getX();
                endY = event.getY();

                findQuadrant fq = new findQuadrant(MatchTextToText.startX, MatchTextToText.startY, canvasTotalX, canvasTotalY);
                int StartQuadrant = fq.getQuadrant();
                findQuadrant fq1 = new findQuadrant(MatchTextToText.endX, endY, canvasTotalX, canvasTotalY);
                int EndQuadrant = fq1.getQuadrant();
                l.setStartX(fq.getNewX());
                l.setStartY(fq.getNewY());
                l.setEndX(fq1.getNewX());
                l.setEndY(fq1.getNewY());
                MatchTextToText.startX = (fq.getNewX());
                MatchTextToText.startY = (fq.getNewY());
                MatchTextToText.endX = (fq1.getNewX());
                endY = (fq1.getNewY());
                Log.wtf("-this", " TotalX : " + canvasTotalX + " |||  TotalY : " + canvasTotalY);
                //   Log.wtf("-this"," StartX : "+MatchTextToText.startX+" || StartY : "+ MatchTextToText.startY+"  |||||  EndX : "+ MatchTextToText.endX+" EndY : "+ MatchTextToText.endY);
                Log.wtf("-this", " start Quad : " + StartQuadrant + " |||  end Quard : " + EndQuadrant);

                if (StartQuadrant != 0 && EndQuadrant != 0 && StartQuadrant != EndQuadrant) {
                    Log.wtf("-this","IF 104");
                    if (!quadExistFingerTemp(StartQuadrant, EndQuadrant)) {
                        Log.wtf("-this","IF 106");
                        if (StartQuadrant < 5 && EndQuadrant > 4) {
                            Log.wtf("-this","IF 108");
                            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            mPaint.setStyle(Style.STROKE);
                            mPaint.setStrokeWidth(strokeWidth);
                            mPaint.setColor(Color.WHITE);
                            l.setmPaint(mPaint);
                            l.setStartQuadrant(StartQuadrant);
                            l.setEndQuadrant(EndQuadrant);
                            line.add(l);
                        } else if (StartQuadrant > 4 && EndQuadrant < 5) {
                            Log.wtf("-this","ELSE 118");
                            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            mPaint.setStyle(Style.STROKE);
                            mPaint.setStrokeWidth(strokeWidth);
                            mPaint.setColor(Color.WHITE);
                            l.setmPaint(mPaint);
                            l.setStartQuadrant(StartQuadrant);
                            l.setEndQuadrant(EndQuadrant);
                            line.add(l);

                        } else {
                            Log.wtf("-this","ELSE 129");
                            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            mPaint.setStyle(Style.STROKE);
                            mPaint.setStrokeWidth(strokeWidth);
                            mPaint.setColor(Color.TRANSPARENT);
                        }
                    } else {
                        Log.wtf("-this","ELSE 136");
                        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        mPaint.setStyle(Style.STROKE);
                        mPaint.setStrokeWidth(strokeWidth);
                        mPaint.setColor(Color.TRANSPARENT);
                    }

                } else {
                    Log.wtf("-this","ELSE 144");
                    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    mPaint.setStyle(Style.STROKE);
                    mPaint.setStrokeWidth(strokeWidth);
                    mPaint.setColor(Color.TRANSPARENT);
                }

                postInvalidate();
                break;
        }


        return true;
    }

    public static boolean quadExistFingerTemp(int s, int e) {
        for (int i = 0; i < line.size(); i++) {
            if ((line.get(i).getStartQuadrant() == s || line.get(i).getStartQuadrant() == e) ||
                    ( line.get(i).getEndQuadrant() == s || line.get(i).getEndQuadrant() == e) ){
                return true;

            }
        }
        return false;
    }

    public static class findQuadrant {

        float X;
        float Y;
        float totalX;
        float totalY;

        float newX;
        float newY;

        float x1Percent = 43;
        float x2Percent = 58;

        float y1Percent = 12;
        float y2Percent = 37;
        float y3Percent = 62;
        float y4Percent = 88;


        float firstQuadrantXMinPercent = 0;
        float firstQuadrantXMaxPercent = 43;
        float firstQuadrantYMinPercent = 0;
        float firstQuadrantYMaxPercent = 23;

        float secondQuadrantXMinPercent = 0;
        float secondQuadrantXMaxPercent = 43;
        float secondQuadrantYMinPercent = 26;
        float secondQuadrantYMaxPercent = 47;

        float thirdQuadrantXMinPercent = 0;
        float thirdQuadrantXMaxPercent = 43;
        float thirdQuadrantYMinPercent = 52;
        float thirdQuadrantYMaxPercent = 73;

        float fourthQuadrantXMinPercent = 0;
        float fourthQuadrantXMaxPercent = 43;
        float fourthQuadrantYMinPercent = 77;
        float fourthQuadrantYMaxPercent = 100;

        float fifthQuadrantXMinPercent = 58;
        float fifthQuadrantXMaxPercent = 100;
        float fifthQuadrantYMinPercent = 0;
        float fifthQuadrantYMaxPercent = 23;

        float sixthQuadrantXMinPercent = 58;
        float sixthQuadrantXMaxPercent = 100;
        float sixthQuadrantYMinPercent = 26;
        float sixthQuadrantYMaxPercent = 47;

        float seventhQuadrantXMinPercent = 58;
        float seventhQuadrantXMaxPercent = 100;
        float seventhQuadrantYMinPercent = 52;
        float seventhQuadrantYMaxPercent = 73;

        float eightQuadrantXMinPercent = 58;
        float eightQuadrantXMaxPercent = 100;
        float eightQuadrantYMinPercent = 77;
        float eightQuadrantYMaxPercent = 100;


        public findQuadrant(float x, float y, float totalX, float totalY) {
            X = x;
            Y = y;
            this.totalX = totalX;
            this.totalY = totalY;
        }

        public int getQuadrant() {
            float xPercent = (X / totalX) * 100;
            float yPercent = (Y / totalY) * 100;
            if (xPercent < firstQuadrantXMaxPercent) {

                newX = (x1Percent * totalX) / 100;
                if (yPercent > firstQuadrantYMinPercent && yPercent < firstQuadrantYMaxPercent) {
                    newY = (y1Percent * totalY) / 100;
                    return 1;
                } else if (yPercent > secondQuadrantYMinPercent && yPercent < secondQuadrantYMaxPercent) {
                    newY = (y2Percent * totalY) / 100;
                    return 2;
                } else if (yPercent > thirdQuadrantYMinPercent && yPercent < thirdQuadrantYMaxPercent) {
                    newY = (y3Percent * totalY) / 100;
                    return 3;
                } else if (yPercent > fourthQuadrantYMinPercent && yPercent < fourthQuadrantYMaxPercent) {
                    newY = (y4Percent * totalY) / 100;
                    return 4;
                }

            } else if (xPercent > fifthQuadrantXMinPercent) {

                newX = (x2Percent * totalX) / 100;
                if (yPercent > fifthQuadrantYMinPercent && yPercent < fifthQuadrantYMaxPercent) {
                    newY = (y1Percent * totalY) / 100;
                    return 5;
                } else if (yPercent > sixthQuadrantYMinPercent && yPercent < sixthQuadrantYMaxPercent) {
                    newY = (y2Percent * totalY) / 100;
                    return 6;
                } else if (yPercent > seventhQuadrantYMinPercent && yPercent < seventhQuadrantYMaxPercent) {
                    newY = (y3Percent * totalY) / 100;
                    return 7;
                } else if (yPercent > eightQuadrantYMinPercent && yPercent < eightQuadrantYMaxPercent) {
                    newY = (y4Percent * totalY) / 100;
                    return 8;
                }
            }


            return 0;
        }

        public float getNewX() {
            return newX;
        }

        public float getNewY() {
            return newY;
        }
    }

}
