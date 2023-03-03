package cc.echarger.echarger.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import cc.echarger.echarger.ui.R;

public class MainBox extends ConstraintLayout {
    public MainBox(@NonNull Context context) {
        super(context);
    }

    public MainBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_box,this,true);
    }

}
