package com.example.pengshan.glidedemo.Progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LoadingImgView2 extends ImageView {
  
    private float per;  
  
    private boolean isfinished = true;
  
    private String colorStr;  
  
    private Paint paintLayer;
    private Paint textPaint;  
  
    private Rect textbound;
  
    private float layer_w;  
    private float layer_h;  
  
    public LoadingImgView2(Context context) {
        super(context);  
        init();  
    }  
  
    public LoadingImgView2(Context context, AttributeSet attrs) {
        super(context, attrs);  
        init();  
    }  
  
    public LoadingImgView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);  
        init();  
    }  
  
    //初始化画笔  
    private void init(){  
        paintLayer = new Paint();  
        paintLayer.setColor(Color.LTGRAY);
        paintLayer.setAlpha(100);  
        textPaint = new Paint();  
        textPaint.setColor(Color.DKGRAY);  
        textPaint.setTextSize(25);  
        textbound = new Rect();  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);  
        if (isfinished)  
            return;  
        String perStr = (int) (per*100) + "%";  
        //获取文字区域的矩形大小，以便确定文字正中间的位置  
        textPaint.getTextBounds(perStr,0, perStr.length(),textbound);  
        layer_w = getWidth();  
        layer_h = getHeight()*per;  
        float y = getHeight() - layer_h;  
        //画遮蔽层  
        canvas.drawRect(0,y,layer_w,getHeight(),paintLayer);  
        //画文字  
        canvas.drawText(perStr, getWidth() / 2 - textbound.width() / 2, getHeight() / 2 + textbound.height() / 2, textPaint);  
    }  
  
    public void setPer(float per){  
        this.per = per;  
        //在主线程刷新
        if(per == 1f){
            finish();
        }else{
            postInvalidate();
        }
    }
  
    public void finish(){  
        isfinished = true;  
        postInvalidate();  
    }  

    public void start(){
        isfinished = false;
        postInvalidate();
    }

} 