package cc.echarger.echarger.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import cc.echarger.echarger.ui.R;

public class Charger extends LinearLayout {

    public Charger(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        addView(View.inflate(context, R.layout.charger,null));
    }

    private void setClickListener(View view){
        this.setOnClickListener(view1->{

        });
    }
}
