package cc.echarger.echarger.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import cc.echarger.echarger.MainActivity;
import cc.echarger.echarger.R;
import cc.echarger.echarger.component.MovableLinearLayout;

public class MoveBoxUtil {
    private float originY;   //手指初始坐标
    private float lastY;    //上一个坐标
    private float boxY;   //模块的起始坐标
    private int screenHeight;  //屏幕高度

    private int statusBarHeight; //状态栏高度
    private float heightUpperBounds; //上限高度
    private float heightLowerBounds; //下限高度
    private float offsetHeight; //上下限高度差

    private boolean isLow=true; //上拉框是否在低位


    public static float dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (dpValue * scale + 0.5f);
    }

    private void translateAnimation(View view,float startPosition,float endPosition){
        ValueAnimator animator = ValueAnimator.ofFloat(startPosition,endPosition);
        animator.setDuration(300);//播放时长

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float currentValue = (float) animation.getAnimatedValue();

                view.setY(currentValue);

                //刷新视图
                view.requestLayout();
            }
        });
        //启动动画
        animator.start();
    }
    @SuppressLint("ClickableViewAccessibility")
    public MoveBoxUtil(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        Log.e("-------", "状态栏-方法1:" + statusBarHeight);

        screenHeight = display.getHeight()+statusBarHeight;

        heightUpperBounds=screenHeight-dp2px(context,700)+statusBarHeight/2;
        heightLowerBounds=screenHeight-dp2px(context,100+20+20)+statusBarHeight/2;
        offsetHeight=heightLowerBounds-heightUpperBounds;

        MovableLinearLayout moveBox = context.findViewById(R.id.move_box);
        moveBox.setY(heightLowerBounds);

        moveBox.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 触点按下
                        boxY= moveBox.getY();
                        lastY=originY= event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE: // 触点移动
                        float offsetY=event.getRawY()-lastY;

                        if(boxY+offsetY>=heightUpperBounds
                                &&boxY+offsetY<heightLowerBounds){
                            boxY+=offsetY;
                            moveBox.setY(boxY);
                        }

                        lastY= event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP: // 触点放开
                        float endY=event.getRawY();
                        float finalOffsetY=endY-originY;
                        if(isLow){
                            if(-finalOffsetY>=offsetHeight*0.25){
                                isLow=false;
                                translateAnimation(moveBox,moveBox.getY(),heightUpperBounds);
                            }else{
                                translateAnimation(moveBox,moveBox.getY(),heightLowerBounds);
                            }

                        }else if(!isLow){
                            if(finalOffsetY>=offsetHeight*0.25) {
                                isLow=true;
                                translateAnimation(moveBox,moveBox.getY(),heightLowerBounds);
                            }else{
                                translateAnimation(moveBox,moveBox.getY(),heightUpperBounds);
                            }

                        }
                }
                return true;
            }
        });

    }
}
