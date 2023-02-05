package cc.echarger.echarger;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import cc.echarger.echarger.databinding.ActivityMainBinding;
import cc.echarger.echarger.ui.util.MoveBoxUtil;

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


        TopNaviUtil topNaviUtil = new TopNaviUtil(MainActivity.this);
//        View mainBox=View.inflate(this,R.layout.main_box,null);  //拿到layout main_box
//        View moveBox=mainBox.findViewById(R.id.move_box);  //拿到main_box中的控件move_box
    }


}
