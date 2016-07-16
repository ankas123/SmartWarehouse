package com.gaia.app.smartwarehouse.resources;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gaia.app.smartwarehouse.R;

/**
 * Created by praveen_gadi on 7/16/2016.
 */
public class Drawrectangle extends View {

    public static final int MIN_VALUE = 0;
    public static final int MAX_VALUE = 180;

    private PointF center = new PointF();
    private RectF Innerrect = new RectF();
    private RectF border = new RectF();
    private Path segment = new Path();
    private Paint strokePaint = new Paint();
    private Paint fillPaint = new Paint();

    private int radius;

    private int fillColor;
    private int strokeColor;
    private float strokeWidth;
    private int value;

    public Drawrectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Drawrectangle,
                0, 0);

        try
        {
            fillColor = a.getColor(R.styleable.Drawrectangle_fillColor_rect, Color.WHITE);
            strokeColor = a.getColor(R.styleable.Drawrectangle_strokeColor_rect, Color.BLACK);
            strokeWidth = a.getFloat(R.styleable.Drawrectangle_strokeWidth_rect, 1f);
            value = a.getInteger(R.styleable.Drawrectangle_value_rect, 0);
            adjustValue(value);
        }
        finally
        {
            a.recycle();
        }

        fillPaint.setColor(fillColor);
        strokePaint.setColor(strokeColor);
        strokePaint.setStrokeWidth(strokeWidth);
        strokePaint.setStyle(Paint.Style.STROKE);
    }
    private void adjustValue(int value)
    {
        this.value = Math.min(MAX_VALUE, Math.max(MIN_VALUE, value));

    }
    public void setValue(int value)
    {
        adjustValue(value);
        setPaths();

        invalidate();
    }

    public int getValue()
    {
        return value;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center.x=getWidth();
        center.y=getHeight();
        border.set(0,0,getWidth(),getHeight());
        setPaths();
    }
    private void setPaths()
    {
        int x= (int) (center.y-(value*center.y/180));
     Innerrect.set(0,x,center.x,center.y);
        Log.e("Canvas Drawing","In setpaths") ;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(Innerrect,fillPaint);
        canvas.drawRect(border,strokePaint);
    }
}
