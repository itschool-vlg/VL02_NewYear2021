package com.example.newyear2021;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

class Circle {
    float x;
    float y;
    float radius = 20.0f;
}

class HolidayText {
    float x;
    float y;
}

public class CustomView extends View {

    private Paint paint;
    private float width;
    private float height;

    private Circle[] circles = new Circle[300];
    private Bitmap treeBitmap;

    private boolean isTextVisible = false;
    private boolean isDown = true;
    private HolidayText hText = new HolidayText();

    public CustomView(Context context) { super(context);
        init(context);
    }
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        treeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tree2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle();
            circles[i].x = (float) (Math.random()*width);
            circles[i].y = (float) (Math.random()*height);
        }

        hText.x = 20.0f;
        hText.y = height*0.5f - 100.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#1A237E"));

        paint.setColor(Color.parseColor("#E0E0E0"));
        float radius = width*0.3f;
        canvas.drawCircle(width*0.5f - radius, height*0.5f - radius*2, radius, paint);

        canvas.drawBitmap(treeBitmap, width*0.5f - treeBitmap.getWidth()*0.5f,
                height - treeBitmap.getHeight(), paint);

        paint.setTextSize(100.0f);
        paint.setColor(Color.parseColor("#D32F2F"));
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC));

        if(isTextVisible) {
            if(isDown) {
                if(hText.y < height)
                    hText.y += 2.5f;
                else isDown = false;
            } else {
                if(hText.y > 0)
                    hText.y -= 2.5f;
                else isDown = true;
            }
        }

        if(isTextVisible)

        if(isTextVisible)
            canvas.drawText("С новым 2021 годом!", hText.x, hText.y, paint);

        paint.setColor(Color.parseColor("#BBDEFB"));
        for(Circle circle : circles) {
            circle.y += 2.5f; // velocity
            canvas.drawCircle(circle.x, circle.y, circle.radius, paint);

            if(circle.y > height) {
                circle.y = (float) (-Math.random()*height);
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hText.y = height*0.5f - 100.0f;
        isTextVisible = true;
        return super.onTouchEvent(event);
    }
}
