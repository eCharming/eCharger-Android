package cc.echarger.echarger;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;


public class StatusBar {
    private final Activity activity;

    public StatusBar(Activity activity) {
        this.activity = activity;
    }

    /**
     * 将状态栏设置为传入的color
     *
     * @param color int
     */
    public void setColor(int color) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false);
        } else {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        window.setStatusBarColor(activity.getResources().getColor(color));
    }

}
