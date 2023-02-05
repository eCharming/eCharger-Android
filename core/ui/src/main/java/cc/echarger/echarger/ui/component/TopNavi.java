package cc.echarger.echarger.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import cc.echarger.echarger.ui.R;

public class TopNavi extends LinearLayout {
    public TopNavi(Context context) {
        super(context, null);
    }

    public TopNavi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, -1);
    }

    public TopNavi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.top_navi, this);
    }

}
