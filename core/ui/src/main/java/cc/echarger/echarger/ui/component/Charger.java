package cc.echarger.echarger.ui.component;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import cc.echarger.echarger.ui.R;
import cc.echarger.echarger.ui.animation.RotateAnimator;

public class Charger extends LinearLayout {
    private View container;
    private View infoPage;
    private View detailPage;
    private ValueAnimator rotateAnimator;

    public Charger(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.charger,this,true);

        container=findViewById(R.id.container);
        infoPage=findViewById(R.id.info_page);
        detailPage=findViewById(R.id.detail_page);

        //创立守护进程，监听该控件是否绘制完毕，绘制完毕后自动调用回调函数，此时getHeight函数可以获取到高度
        this.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "Charger: "+detailPage.getHeight());
//                detailPage.setPivotX(0);
//                detailPage.setPivotY(((float)detailPage.getHeight())/2);
//                detailPage.setTranslationZ(-((float)infoPage.getWidth())/2);
                detailPage.setRotationX(180);
//                Charger.this.setElevation(detailPage.getWidth());
//                Charger.this.setPivotX(((float)infoPage.getWidth())/2);
//                Charger.this.setPivotY(((float)infoPage.getHeight())/2);
            }
        });
        infoPage.setVisibility(View.VISIBLE);
        detailPage.setVisibility(View.INVISIBLE);
        container.setCameraDistance(16000);
//        this.setCameraDistance(10);


        setClickListener();

    }


    private void setClickListener(){
        infoPage.setOnClickListener(view->{

            ValueAnimator rotateAnimator=ValueAnimator.ofFloat(0,-180);
            rotateAnimator.addUpdateListener(animation -> {
                float currentValue = (float)animation.getAnimatedValue();
                if(currentValue/-180>=0.5){
//                    detailPage.setElevation(2);
                    infoPage.setVisibility(View.INVISIBLE);
                    detailPage.setVisibility(View.VISIBLE);
                }
//            Matrix matrix=new Matrix();
//            matrix.setRotate(currentValue);
                container.setRotationX(currentValue);
//            container.setAnimationMatrix(matrix);
                //刷新视图
                container.requestLayout();
            });
            rotateAnimator.setDuration(500);
            rotateAnimator.start();
//            RotateAnimator rotateAnimator=new RotateAnimator(0,-90);
//
//            rotateAnimator.setDuration(500);
//            rotateAnimator.setFillAfter(true);
//            container.startAnimation(rotateAnimator);
        });

        detailPage.setOnClickListener(view->{
            ValueAnimator rotateAnimator=ValueAnimator.ofFloat(-180,0);
            rotateAnimator.addUpdateListener(animation -> {
                float currentValue = (float)animation.getAnimatedValue();
                if(currentValue/-180<=0.5){
//                    detailPage.setElevation(0);
                    infoPage.setVisibility(View.VISIBLE);
                    detailPage.setVisibility(View.INVISIBLE);
                }
//            Matrix matrix=new Matrix();
//            matrix.setRotate(currentValue);
                container.setRotationX(currentValue);
//            container.setAnimationMatrix(matrix);
                //刷新视图
                container.requestLayout();
            });
            rotateAnimator.setDuration(500);
            rotateAnimator.start();
        });
    }

    private void initAnimator(){

    }
}
