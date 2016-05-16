/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sfj.qrcode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.sfj.R;
import com.sfj.qrcode.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private static final long ANIMATION_DELAY = 80L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int MAX_RESULT_POINTS = 20;
    private static final int POINT_SIZE = 6;

    private CameraManager cameraManager;
    private final Paint paint;
    private final int frameColor;
    private final int maskColor;
    private final int resultColor;
    private final int laserColor;
    /**
     * 增大将增加边框角的宽度
     */
    private static final int frameCornerWidth=12;
    /**
     * 增大 将增加边角框的长宽比
     */
    private static final int length_width_power = 8;
    private int scannerAlpha;
    private List<ResultPoint> possibleResultPoints;
    private List<ResultPoint> lastPossibleResultPoints;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();
        frameColor = resources.getColor(R.color.white);
        maskColor = resources.getColor(R.color.qrcode_mask);
        resultColor = resources.getColor(R.color.qrcode_rslt);
        laserColor = resources.getColor(R.color.qrcode_scanner);
        scannerAlpha = 0;
        possibleResultPoints = new ArrayList<>(5);
        lastPossibleResultPoints = null;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public Rect getFrameRect(){
        if(cameraManager!=null)
            return cameraManager.getFramingRect();
        else
            return null;
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int centerX = frame.left+frame.width()/2;
        int centerY = frame.top + frame.height()/2;

        //画四边阴影
        paint.setColor(maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paint.setColor(frameColor);
        paint.setStrokeWidth(1);
        //画边线 4条
        canvas.drawLine(frame.left, frame.top, frame.right, frame.top, paint);
        canvas.drawLine(frame.right, frame.top, frame.right, frame.bottom, paint);
        canvas.drawLine(frame.left, frame.bottom, frame.right, frame.bottom, paint);
        canvas.drawLine(frame.left, frame.top, frame.left, frame.bottom, paint);

        //画四个角
        paint.setColor(laserColor);
        paint.setStrokeWidth(frameCornerWidth/2);
        //左上
        canvas.drawLine(frame.left-frameCornerWidth/2,frame.top-frameCornerWidth/2,
                frame.left+frameCornerWidth*length_width_power,frame.top-frameCornerWidth/2,paint);
        canvas.drawLine(frame.left-frameCornerWidth/2,frame.top-frameCornerWidth,
                frame.left-frameCornerWidth/2,frame.top+frameCornerWidth*length_width_power,paint);
//        //右上
//        canvas.drawLine(frame.right-frameCornerWidth*length_width_power,frame.top-frameCornerWidth/2,
//                frame.right+frameCornerWidth/2,frame.top-frameCornerWidth/2,paint);
//        canvas.drawLine(frame.right+frameCornerWidth/2,frame.top-frameCornerWidth,
//                frame.right+frameCornerWidth/2,frame.top+frameCornerWidth*length_width_power,paint);
//
//
//        //左下
//        canvas.drawLine(frame.left-frameCornerWidth/2,frame.bottom+frameCornerWidth/2,
//                frame.left+frameCornerWidth*length_width_power,frame.bottom+frameCornerWidth/2,paint);
//        canvas.drawLine(frame.left-frameCornerWidth/2,frame.bottom-frameCornerWidth*length_width_power,
//                frame.left-frameCornerWidth/2,frame.bottom+frameCornerWidth,paint);
//
//        //左右
//        canvas.drawLine(frame.right-frameCornerWidth*length_width_power,frame.bottom+frameCornerWidth/2,
//                frame.right+frameCornerWidth/2,frame.bottom+frameCornerWidth/2,paint);
//        canvas.drawLine(frame.right+frameCornerWidth/2,frame.bottom-frameCornerWidth*length_width_power,
//                frame.right+frameCornerWidth/2,frame.bottom+frameCornerWidth,paint);


        int cnt = 4;
        canvas.save();
        paint.setColor(laserColor);
        for (int i = 0; i < cnt; i++) {
            canvas.rotate(90 * i, centerX, centerY);
            paint.setStrokeWidth(frameCornerWidth);
            canvas.drawLine(frame.left-frameCornerWidth/2,
                    frame.top+frameCornerWidth*length_width_power,
                    frame.left-frameCornerWidth/2,
                    frame.top-frameCornerWidth,paint);
            canvas.drawLine(frame.left,
                    frame.top-frameCornerWidth/2,
                    frame.left+frameCornerWidth*length_width_power,
                    frame.top-frameCornerWidth/2,paint);
        }
        canvas.restore();
    }

    public void drawViewfinder() {
        invalidate();
    }

/*
    public void addPossibleResultPoint(ResultPoint point) {
        List<ResultPoint> points = possibleResultPoints;
        synchronized (points) {
            points.add(point);
            int size = points.size();
            if (size > MAX_RESULT_POINTS) {
                // trim it
                points.subList(0, size - MAX_RESULT_POINTS / 2).clear();
            }
        }
    }*/

}
