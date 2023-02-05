package cc.echarger.echarger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import cc.echarger.echarger.databinding.ActivityMainBinding;
import cc.echarger.echarger.ui.component.MovableLinearLayout;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        StatusBar statusBar = new StatusBar(MainActivity.this);
        //设置颜色为透明
        statusBar.setColor(R.color.transparent);

        MovableLinearLayout moveBox = findViewById(R.id.include);
        moveBox.setTopNavi(findViewById(R.id.top_navi));
    }


}
