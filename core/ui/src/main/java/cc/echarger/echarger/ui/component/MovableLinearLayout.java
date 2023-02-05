package cc.echarger.echarger.ui.component;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import cc.echarger.echarger.ui.util.UnitConversionUtil;

public class MovableLinearLayout extends LinearLayout {
    private final Context context; //上下文
    private float originY;   //手指初始坐标
    private float lastY;    //上一个坐标
    private float boxY;   //模块的起始坐标
    private float heightUpperBounds; //上限高度
    private float heightLowerBounds; //下限高度
    private float offsetHeight; //上下限高度差
    private boolean isLow = true; //上拉框是否在低位

    private TopNavi topNavi;


    @SuppressLint("ClickableViewAccessibility")
    public MovableLinearLayout(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public MovableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;

        //获取status_bar_height资源的ID
        @SuppressLint("InternalInsetResource") int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        //状态栏高度
        int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);

        //屏幕高度
        int screenHeight = height + statusBarHeight;

        heightUpperBounds = screenHeight - UnitConversionUtil.dp2px(context, 700) + statusBarHeight / 2.0f;
        heightLowerBounds = screenHeight - UnitConversionUtil.dp2px(context, 100 + 20 + 20) + statusBarHeight / 2.0f;
        offsetHeight = heightLowerBounds - heightUpperBounds;

        this.setY(heightLowerBounds);

        this.setOnTouchListener((view, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // 触点按下
                    boxY = this.getY();
                    lastY = originY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE: // 触点移动
                    float offsetY = event.getRawY() - lastY;
                    if (boxY + offsetY >= heightUpperBounds
                            && boxY + offsetY < heightLowerBounds) {
                        boxY += offsetY;
                        this.setY(boxY);
                    }
                    lastY = event.getRawY();
                    break;
                case MotionEvent.ACTION_UP: // 触点放开
                    float endY = event.getRawY();
                    float finalOffsetY = endY - originY;
                    if (isLow) {
                        if (-finalOffsetY >= offsetHeight * 0.25) {
                            isLow = false;
                            translateAnimation(this.getY(), heightUpperBounds);
                            topNavi.foldAnimation(false);
                        } else {
                            translateAnimation(this.getY(), heightLowerBounds);
                        }
                    } else {
                        if (finalOffsetY >= offsetHeight * 0.25) {
                            isLow = true;
                            translateAnimation(this.getY(), heightLowerBounds);
                            topNavi.foldAnimation(true);
                        } else {
                            translateAnimation(this.getY(), heightUpperBounds);
                        }
                    }
            }
            return true;
        });
    }

    public void setTopNavi(TopNavi topNavi){
        this.topNavi = topNavi;
    }

    private void translateAnimation(float startPosition, float endPosition) {
        ValueAnimator animator = ValueAnimator.ofFloat(startPosition, endPosition);
        animator.setDuration(300);//播放时长

        animator.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            this.setY(currentValue);

            //刷新视图
            this.requestLayout();
        });
        //启动动画
        animator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

}
