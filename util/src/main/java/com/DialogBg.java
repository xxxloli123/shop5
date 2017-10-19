package com;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sgrape on 2017/5/14.
 * e-mail: sgrape1153@gmail.com
 */

public class DialogBg extends Drawable {
    private RectF rectF;
    private Paint paint;
    private float radius;

    public DialogBg() {
        this(0, Color.WHITE);
    }

    public DialogBg(float radius, int color) {
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
        paint.setAlpha(127);
        rectF = new RectF();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(rectF, radius, radius, paint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF.set(left, top, right, bottom);
    }

    @Override
    public void setBounds(@NonNull Rect bounds) {
        super.setBounds(bounds);
        rectF.set(bounds);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        rectF.set(bounds);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidateSelf();
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidateSelf();
    }
}
