package cc.echarger.echarger.ui.component;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import cc.echarger.echarger.ui.R;

public class Charger extends LinearLayout {
    private View infoPage;
    private View detailPage;
    private Animator rotateAnimator;

    public Charger(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.charger,this,true);

        infoPage=findViewById(R.id.info_page);
        detailPage=findViewById(R.id.detail_page);

        //创立守护进程，监听该控件是否绘制完毕，绘制完毕后自动调用回调函数，此时getHeight函数可以获取到高度
        this.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "Charger: "+detailPage.getHeight());
                detailPage.setPivotX(0);
                detailPage.setPivotY(((float)detailPage.getHeight())/2);

                Charger.this.setPivotX(((float)infoPage.getWidth())/2);
                Charger.this.setPivotY(((float)infoPage.getHeight())/2);
            }
        });
        detailPage.setRotationY(90);
    }

    private void setClickListener(View view){
        infoPage.setOnClickListener(view1->{
            ValueAnimator rotateAnimator=ValueAnimator.ofFloat(0,-90);
            rotateAnimator.addUpdateListener(animation -> {
                float currentValue = (float)animation.getAnimatedValue();
                this.setRotationY(currentValue);
                //刷新视图
                this.requestLayout();
            });
        });
    }

    private void initAnimator(){

    }
}
