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

import static com.orenda.taimo.grade3tograde5.Tests.MatchTextToTextTwoOptions.canvasTotalX;
import static com.orenda.taimo.grade3tograde5.Tests.MatchTextToTextTwoOptions.canvasTotalY;
import static com.orenda.taimo.grade3tograde5.Tests.MatchTextToTextTwoOptions.endY;


public class FingerLineTempTwoOptions extends View {
    public static Paint mPaint;
    float strokeWidth = 5;
    Canvas canvas;
    int count = 0;

    public static ArrayList<LineModel> line = null;

    public FingerLineTempTwoOptions(Context context) {
        this(context, null);
    }

    public FingerLineTempTwoOptions(Context context, AttributeSet attrs) {
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
        canvas.drawLine(MatchTextToTextTwoOptions.startX, MatchTextToTextTwoOptions.startY, MatchTextToTextTwoOptions.endX, endY, mPaint);
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
                MatchTextToTextTwoOptions.startX = event.getX();
                MatchTextToTextTwoOptions.startY = event.getY();
                // Set the end to prevent initial jump (like on the demo recording)
                MatchTextToTextTwoOptions.endX = event.getX();
                endY = event.getY();
                Log.wtf("-this", " StartX : " + MatchTextToText.startX + " || StartY : " + MatchTextToText.startY + "  |||||  EndX : " + MatchTextToText.endX + " EndY : " + MatchTextToText.endY);

                l.setmPaint(mPaint);
                l.setStartX(MatchTextToTextTwoOptions.startX);
                l.setStartY(MatchTextToTextTwoOptions.startY);
                l.setEndX(MatchTextToTextTwoOptions.endX);
                l.setEndY(endY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                MatchTextToTextTwoOptions.endX = event.getX();
                endY = event.getY();
                l.setEndX(MatchTextToTextTwoOptions.endX);
                l.setEndY(endY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                MatchTextToTextTwoOptions.endX = event.getX();
                endY = event.getY();

                findQuadrantTwoOptions fq = new findQuadrantTwoOptions(MatchTextToTextTwoOptions.startX, MatchTextToTextTwoOptions.startY, canvasTotalX, canvasTotalY);
                int StartQuadrant = fq.getQuadrant();
                findQuadrantTwoOptions fq1 = new findQuadrantTwoOptions(MatchTextToTextTwoOptions.endX, endY, canvasTotalX, canvasTotalY);
                int EndQuadrant = fq1.getQuadrant();
                l.setStartX(fq.getNewX());
                l.setStartY(fq.getNewY());
                l.setEndX(fq1.getNewX());
                l.setEndY(fq1.getNewY());
                MatchTextToTextTwoOptions.startX = (fq.getNewX());
                MatchTextToTextTwoOptions.startY = (fq.getNewY());
                MatchTextToTextTwoOptions.endX = (fq1.getNewX());
                endY = (fq1.getNewY());
                Log.wtf("-this", " TotalX : " + canvasTotalX + " |||  TotalY : " + canvasTotalY);
                //   Log.wtf("-this"," StartX : "+MatchTextToTextTwoOptions.startX+" || StartY : "+ MatchTextToTextTwoOptions.startY+"  |||||  EndX : "+ MatchTextToTextTwoOptions.endX+" EndY : "+ MatchTextToTextTwoOptions.endY);
                Log.wtf("-this", " start Quad : " + StartQuadrant + " |||  end Quard : " + EndQuadrant);

                if (StartQuadrant != 0 && EndQuadrant != 0 && StartQuadrant != EndQuadrant) {
                    if (!quadExist(StartQuadrant, EndQuadrant)) {
                        if (StartQuadrant < 3 && EndQuadrant > 2) {

                            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            mPaint.setStyle(Style.STROKE);
                            mPaint.setStrokeWidth(strokeWidth);
                            mPaint.setColor(Color.WHITE);
                            l.setmPaint(mPaint);
                            l.setStartQuadrant(StartQuadrant);
                            l.setEndQuadrant(EndQuadrant);
                            line.add(l);
                        } else if (StartQuadrant > 2 && EndQuadrant < 3) {
                            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            mPaint.setStyle(Style.STROKE);
                            mPaint.setStrokeWidth(strokeWidth);
                            mPaint.setColor(Color.WHITE);
                            l.setmPaint(mPaint);
                            l.setStartQuadrant(StartQuadrant);
                            l.setEndQuadrant(EndQuadrant);
                            line.add(l);
                        } else {
                            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                            mPaint.setStyle(Style.STROKE);
                            mPaint.setStrokeWidth(strokeWidth);
                            mPaint.setColor(Color.TRANSPARENT);
                        }
                    } else {
                        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        mPaint.setStyle(Style.STROKE);
                        mPaint.setStrokeWidth(strokeWidth);
                        mPaint.setColor(Color.TRANSPARENT);
                    }

                } else {
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

    public static boolean quadExist(int s, int e) {
        for (int i = 0; i < line.size(); i++) {
            if (line.get(i).getStartQuadrant() == s || line.get(i).getStartQuadrant() == e
                    || line.get(i).getEndQuadrant() == s || line.get(i).getEndQuadrant() == e) {
                return true;

            }
        }
        return false;
    }

    public static class findQuadrantTwoOptions {

        float X;
        float Y;
        float totalX;
        float totalY;

        float newX;
        float newY;

        float x1Percent = 43;
        float x2Percent = 58;

        float y1Percent = 23;
        float y2Percent = 77;


        float firstQuadrantXMinPercent = 0;
        float firstQuadrantXMaxPercent = 43;
        float firstQuadrantYMinPercent = 0;
        float firstQuadrantYMaxPercent = 43;

        float secondQuadrantXMinPercent = 0;
        float secondQuadrantXMaxPercent = 43;
        float secondQuadrantYMinPercent = 55;
        float secondQuadrantYMaxPercent = 100;


        float thirdQuadrantXMinPercent = 58;
        float thirdQuadrantXMaxPercent = 100;
        float thirdQuadrantYMinPercent = 0;
        float thirdQuadrantYMaxPercent = 43;

        float fourthQuadrantXMinPercent = 58;
        float fourthQuadrantXMaxPercent = 100;
        float fourthQuadrantYMinPercent = 55;
        float fourthQuadrantYMaxPercent = 100;


        public findQuadrantTwoOptions(float x, float y, float totalX, float totalY) {
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
                }

            } else if (xPercent > thirdQuadrantXMinPercent) {

                newX = (x2Percent * totalX) / 100;
                if (yPercent > thirdQuadrantYMinPercent && yPercent < thirdQuadrantYMaxPercent) {
                    newY = (y1Percent * totalY) / 100;
                    return 3;
                } else if (yPercent > fourthQuadrantYMinPercent && yPercent < fourthQuadrantYMaxPercent) {
                    newY = (y2Percent * totalY) / 100;
                    return 4;
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
