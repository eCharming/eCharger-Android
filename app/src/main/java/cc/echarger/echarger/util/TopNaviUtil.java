package cc.echarger.echarger.util;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import cc.echarger.echarger.R;

import static cc.echarger.echarger.util.UnitConversionUtil.dp2px;

public class TopNaviUtil {
    private Activity context; //activity的上下文
    private int choice=1;  //选择哪个选项
    private View order;
    private View chat;
    private View find;
    private View my;
    private ConstraintLayout choiceBall;
    private ConstraintLayout foldBar;
    private int screenWidth; //屏幕宽度

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    private View selectChoice(int choice){
        switch (choice){
            case 1:
                return order;
            case 2:
                return chat;
            case 3:
                return find;
            case 4:
                return my;
        }
        return order;
    }

    private void alterChoiceAnimation(int lastChoice,int currentChoice){
        RelativeLayout.LayoutParams choiceBallParams = (RelativeLayout.LayoutParams)choiceBall.getLayoutParams();
        int leftMargin = choiceBallParams.leftMargin;
        ValueAnimator choiceBallAnimator = ValueAnimator.ofInt(leftMargin,(int)((screenWidth/4-dp2px(context,80))/2+(currentChoice-1)*screenWidth/4));

        choiceBallAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int currentValue = (int) animation.getAnimatedValue();
                choiceBallParams.setMargins(currentValue,0,0,0);
                choiceBall.setLayoutParams(choiceBallParams);
                //刷新视图
                choiceBall.requestLayout();
            }
        });


        RelativeLayout lastChoiceView = (RelativeLayout)selectChoice(lastChoice);
        LinearLayout.LayoutParams lastChoiceParams = (LinearLayout.LayoutParams)lastChoiceView.getLayoutParams();
        TextView lastTextView=(TextView)lastChoiceView.getChildAt(1);

        RelativeLayout currentChoiceView = (RelativeLayout)selectChoice(currentChoice);
        LinearLayout.LayoutParams currentChoiceParams = (LinearLayout.LayoutParams)currentChoiceView.getLayoutParams();
        TextView currentTextView=(TextView)currentChoiceView.getChildAt(1);

        ValueAnimator lastChoiceAnimator = ValueAnimator.ofInt((int)dp2px(context,35),0);
        ValueAnimator lastTextAnimator = ValueAnimator.ofFloat(1,0);

        ValueAnimator currentChoiceAnimator = ValueAnimator.ofInt(0,(int)dp2px(context,35));
        ValueAnimator currentTextAnimator = ValueAnimator.ofFloat(0,1);

        lastChoiceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int currentValue = (int) animation.getAnimatedValue();
                lastChoiceParams.setMargins(0,currentValue,0,0);
                lastChoiceView.setLayoutParams(lastChoiceParams);
                //刷新视图
                lastChoiceView.requestLayout();
            }
        });

        lastTextAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float currentValue = (float) animation.getAnimatedValue();
                lastTextView.setAlpha(currentValue);
                //刷新视图
                lastTextView.requestLayout();
            }
        });

        currentChoiceAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int currentValue = (int) animation.getAnimatedValue();
                currentChoiceParams.setMargins(0,currentValue,0,0);
                currentChoiceView.setLayoutParams(currentChoiceParams);
                //刷新视图
                currentChoiceView.requestLayout();
            }
        });

        currentTextAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float currentValue = (float) animation.getAnimatedValue();
                currentTextView.setAlpha(currentValue);
                //刷新视图
                currentTextView.requestLayout();
            }
        });

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(choiceBallAnimator).with(lastChoiceAnimator).with(currentChoiceAnimator).with(lastTextAnimator).with(currentTextAnimator);
        //启动动画
        animatorSet.start();
    }

    public void foldAnimation(boolean toLow){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)foldBar.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        ValueAnimator animator;
        if(toLow)     //如果去往低处
            animator = ValueAnimator.ofInt(topMargin,0);
        else
            animator = ValueAnimator.ofInt(0,(int)dp2px(context,-45));

        animator.setDuration(300);//播放时长

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int currentValue = (int) animation.getAnimatedValue();
                layoutParams.setMargins(0,currentValue,0,0);
                foldBar.setLayoutParams(layoutParams);
                //刷新视图
                foldBar.requestLayout();
            }
        });
        //启动动画
        animator.start();
    }

    public void setClickListener(View view, int choice){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getChoice()==choice)
                    return;

                alterChoiceAnimation(getChoice(),choice);
                setChoice(choice);

            }
        });
    }

    public TopNaviUtil(Activity context) {
        this.context=context;
        order= context.findViewById(R.id.order);
        chat=context.findViewById(R.id.chat);
        find=context.findViewById(R.id.find);
        my=context.findViewById(R.id.my);
        choiceBall=context.findViewById(R.id.choice_ball);
        foldBar=context.findViewById(R.id.fold_bar);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;

        setClickListener(order,1);
        setClickListener(chat,2);
        setClickListener(find,3);
        setClickListener(my,4);
    }


}
