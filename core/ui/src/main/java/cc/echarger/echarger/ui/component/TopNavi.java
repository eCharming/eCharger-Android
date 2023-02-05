package cc.echarger.echarger.ui.component;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import cc.echarger.echarger.ui.R;
import cc.echarger.echarger.ui.util.UnitConversionUtil;

public class TopNavi extends ConstraintLayout {

    private Context context;
    private int choice = 1;  //选择哪个选项
    private View order;
    private View chat;
    private View find;
    private View my;
    private ConstraintLayout choiceBall;
    private ConstraintLayout foldBar;
    private int screenWidth; //屏幕宽度

    public TopNavi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addView(View.inflate(context, R.layout.top_navi, null));
        order = findViewById(R.id.order);
        chat = findViewById(R.id.chat);
        find = findViewById(R.id.find);
        my = findViewById(R.id.my);
        choiceBall = findViewById(R.id.choice_ball);
        foldBar = findViewById(R.id.fold_bar);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        this.context = context;
        setClickListener(order, 1);
        setClickListener(chat, 2);
        setClickListener(find, 3);
        setClickListener(my, 4);
    }

    private void setClickListener(View view, int choice) {
        view.setOnClickListener(view1 -> {
            if (this.choice == choice)
                return;
            alterChoiceAnimation(this.choice, choice);
            this.choice = choice;
        });
    }

    private void alterChoiceAnimation(int lastChoice, int currentChoice) {
        RelativeLayout.LayoutParams choiceBallParams = (RelativeLayout.LayoutParams) choiceBall.getLayoutParams();
        int leftMargin = choiceBallParams.leftMargin;
        ValueAnimator choiceBallAnimator = ValueAnimator.ofInt(leftMargin, (int) ((screenWidth / 4 - UnitConversionUtil.dp2px(context, 80)) / 2 + (currentChoice - 1) * screenWidth / 4));

        choiceBallAnimator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            choiceBallParams.setMargins(currentValue, 0, 0, 0);
            choiceBall.setLayoutParams(choiceBallParams);
            //刷新视图
            choiceBall.requestLayout();
        });


        RelativeLayout lastChoiceView = (RelativeLayout) selectChoice(lastChoice);
        LinearLayout.LayoutParams lastChoiceParams = (LinearLayout.LayoutParams) lastChoiceView.getLayoutParams();
        TextView lastTextView = (TextView) lastChoiceView.getChildAt(1);

        RelativeLayout currentChoiceView = (RelativeLayout) selectChoice(currentChoice);
        LinearLayout.LayoutParams currentChoiceParams = (LinearLayout.LayoutParams) currentChoiceView.getLayoutParams();
        TextView currentTextView = (TextView) currentChoiceView.getChildAt(1);

        ValueAnimator lastChoiceAnimator = ValueAnimator.ofInt((int) UnitConversionUtil.dp2px(context, 35), 0);
        ValueAnimator lastTextAnimator = ValueAnimator.ofFloat(1, 0);

        ValueAnimator currentChoiceAnimator = ValueAnimator.ofInt(0, (int) UnitConversionUtil.dp2px(context, 35));
        ValueAnimator currentTextAnimator = ValueAnimator.ofFloat(0, 1);

        lastChoiceAnimator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            lastChoiceParams.setMargins(0, currentValue, 0, 0);
            lastChoiceView.setLayoutParams(lastChoiceParams);
            //刷新视图
            lastChoiceView.requestLayout();
        });

        lastTextAnimator.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            lastTextView.setAlpha(currentValue);
            //刷新视图
            lastTextView.requestLayout();
        });

        currentChoiceAnimator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            currentChoiceParams.setMargins(0, currentValue, 0, 0);
            currentChoiceView.setLayoutParams(currentChoiceParams);
            //刷新视图
            currentChoiceView.requestLayout();
        });

        currentTextAnimator.addUpdateListener(animation -> {
            float currentValue = (float) animation.getAnimatedValue();
            currentTextView.setAlpha(currentValue);
            //刷新视图
            currentTextView.requestLayout();
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.play(choiceBallAnimator)
                .with(lastChoiceAnimator)
                .with(currentChoiceAnimator)
                .with(lastTextAnimator).
                with(currentTextAnimator);
        //启动动画
        animatorSet.start();
    }

    private View selectChoice(int choice) {
        switch (choice) {
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

    public void foldAnimation(boolean toLow) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) foldBar.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        ValueAnimator animator;
        if (toLow) {    //如果去往低处
            animator = ValueAnimator.ofInt(topMargin, 0);
        } else {
            animator = ValueAnimator.ofInt(0, (int) UnitConversionUtil.dp2px(context, -45));
        }

        animator.setDuration(300);//播放时长

        animator.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            layoutParams.setMargins(0, currentValue, 0, 0);
            foldBar.setLayoutParams(layoutParams);
            //刷新视图
            foldBar.requestLayout();
        });
        //启动动画
        animator.start();
    }

}
