package cc.echarger.echarger;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import cc.echarger.echarger.component.MovableLinearLayout;
import cc.echarger.echarger.component.StatusBar;
import cc.echarger.echarger.util.MoveBoxUtil;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import cc.echarger.echarger.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    private float originY;   //手指初始坐标
    private float lastY;    //上一个坐标
    private float boxY;   //模块的起始坐标
    private int screenHeight;  //屏幕高度
    private float heightUpperBounds; //上限高度
    private float heightLowerBounds; //下限高度

    public static float dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (dpValue * scale + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBar statusBar = new StatusBar(MainActivity.this);
        //设置颜色为透明
        statusBar.setColor(R.color.transparent);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MoveBoxUtil moveBoxUtil=new MoveBoxUtil(MainActivity.this);


//        Display display = getWindowManager().getDefaultDisplay();
//        Point point = new Point();
//        display.getSize(point);
//
//        //获取status_bar_height资源的ID
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        final int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//        Log.e("-------", "状态栏-方法1:" + statusBarHeight);
//
//        screenHeight = display.getHeight()+statusBarHeight;
//
//        heightUpperBounds=screenHeight-dp2px(MainActivity.this,700)+statusBarHeight/2;
//        heightLowerBounds=screenHeight-dp2px(MainActivity.this,100+20+20)+statusBarHeight/2;
//
//        Log.e("TAG", "height:"+screenHeight );
//
//        View btn1 = findViewById(R.id.btn1);
//        btn1.setOnClickListener(v->{
//            Log.e("btn1","1");
//        });
//
//        MovableLinearLayout moveBox = findViewById(R.id.move_box);
//
//        Log.e("TAG", ":"+moveBox.getY() );
//
//        moveBox.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: // 触点按下
//                        boxY= moveBox.getY();
//                        lastY=originY= event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_MOVE: // 触点移动
//                        float offsetY=event.getRawY()-lastY;
//
//                        if(boxY+offsetY>=heightUpperBounds
//                                &&boxY+offsetY<heightLowerBounds){
//                            boxY+=offsetY;
//                            moveBox.setY(boxY);
//                        }
//
//                        lastY= event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_UP: // 触点放开
//                        float endY=event.getRawY();
//
////                        if()
////                        Animation translateAnimation = new TranslateAnimation(0,0,0,500);
//
//                }
//                return true;
//            }
//        });

//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
