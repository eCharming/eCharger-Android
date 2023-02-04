package cc.echarger.echarger;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import cc.echarger.echarger.databinding.ActivityMainBinding;
import cc.echarger.echarger.ui.component.StatusBar;
import cc.echarger.echarger.ui.util.MoveBoxUtil;
import cc.echarger.echarger.ui.util.TopNaviUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBar statusBar = new StatusBar(MainActivity.this);
        //设置颜色为透明
        statusBar.setColor(R.color.transparent);

        cc.echarger.echarger.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            MoveBoxUtil moveBoxUtil=new MoveBoxUtil(MainActivity.this);
            TopNaviUtil topNaviUtil = new TopNaviUtil(MainActivity.this);
            moveBoxUtil.setTopNaviUtil(topNaviUtil);
        }
    }

}
