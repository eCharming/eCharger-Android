package cc.echarger.echarger.component;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import cc.echarger.echarger.R;

public class MovableLinearLayout extends LinearLayout {

    public MovableLinearLayout(Context context) {
        super(context);
    }

    public MovableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MovableLinearLayout(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }

}
