package cc.echarger.echarger.ui.util;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import cc.echarger.echarger.ui.R;
import cc.echarger.echarger.ui.component.MovableLinearLayout;

public class MoveBoxUtil {
//    private float originY;   //手指初始坐标
//    private float lastY;    //上一个坐标
//    private float boxY;   //模块的起始坐标
//    private final float heightUpperBounds; //上限高度
//    private final float heightLowerBounds; //下限高度
//    private final float offsetHeight; //上下限高度差
//
//    private boolean isLow = true; //上拉框是否在低位
//
//    private TopNaviUtil topNaviUtil;
//
//
//    public void setTopNaviUtil(TopNaviUtil topNaviUtil) {
//        this.topNaviUtil = topNaviUtil;
//    }
//
//    private void translateAnimation(View view, float startPosition, float endPosition) {
//        ValueAnimator animator = ValueAnimator.ofFloat(startPosition, endPosition);
//        animator.setDuration(300);//播放时长
//
//        animator.addUpdateListener(animation -> {
//            float currentValue = (float) animation.getAnimatedValue();
//            view.setY(currentValue);
//
//            //刷新视图
//            view.requestLayout();
//        });
//        //启动动画
//        animator.start();
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    public MoveBoxUtil(Activity context) {
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int height = displayMetrics.heightPixels;
//
//        //获取status_bar_height资源的ID
//        @SuppressLint("InternalInsetResource") int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
//        //状态栏高度
//        int statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
//
//        //屏幕高度
//        int screenHeight = height + statusBarHeight;
//
//        heightUpperBounds = screenHeight - UnitConversionUtil.dp2px(context, 700) + statusBarHeight / 2.0f;
//        heightLowerBounds = screenHeight - UnitConversionUtil.dp2px(context, 100 + 20 + 20) + statusBarHeight / 2.0f;
//        offsetHeight = heightLowerBounds - heightUpperBounds;
//
//        MovableLinearLayout moveBox = context.findViewById(R.id.move_box);
//        moveBox.setY(heightLowerBounds);
//
//        moveBox.setOnTouchListener((view, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN: // 触点按下
//                    boxY = moveBox.getY();
//                    lastY = originY = event.getRawY();
//                    break;
//                case MotionEvent.ACTION_MOVE: // 触点移动
//                    float offsetY = event.getRawY() - lastY;
//                    if (boxY + offsetY >= heightUpperBounds
//                            && boxY + offsetY < heightLowerBounds) {
//                        boxY += offsetY;
//                        moveBox.setY(boxY);
//                    }
//                    lastY = event.getRawY();
//                    break;
//                case MotionEvent.ACTION_UP: // 触点放开
//                    float endY = event.getRawY();
//                    float finalOffsetY = endY - originY;
//                    if (isLow) {
//                        if (-finalOffsetY >= offsetHeight * 0.25) {
//                            isLow = false;
//                            translateAnimation(moveBox, moveBox.getY(), heightUpperBounds);
//                            topNaviUtil.foldAnimation(false);
//                        } else {
//                            translateAnimation(moveBox, moveBox.getY(), heightLowerBounds);
//                        }
//                    } else {
//                        if (finalOffsetY >= offsetHeight * 0.25) {
//                            isLow = true;
//                            translateAnimation(moveBox, moveBox.getY(), heightLowerBounds);
//                            topNaviUtil.foldAnimation(true);
//                        } else {
//                            translateAnimation(moveBox, moveBox.getY(), heightUpperBounds);
//                        }
//                    }
//            }
//            return true;
//        });
//
//    }
}
