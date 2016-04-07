package com.example.user.ble_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

    private int count = 0;
    private int POINT_Size;
    private int pointX = 380;
    private int pointY = 100;
    private int first = 0;
    private boolean ShowMap = false;
    private boolean Back = false;
    private int MoveType = 1;    //定义区分移动的类型
    private boolean Index = true;  //判断是否发生移动
    private int xSpeed = 7;
    private int ySpeed = 7;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<View>();
    private View view;
    //三个线性布局，每个代表一个页面的按钮
    private LinearLayout mFirst;
    private LinearLayout mSecond;
    private LinearLayout mThird;
    //三个按键
    private ImageButton FirstButtom;
    private ImageButton SecondButtom;
    private ImageButton ThirdButtom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        setContentView(R.layout.activity_main);
        initView();
        initViewPage();
        initEvent();
        final MapView mapView = new MapView(this);

        POINT_Size = 10;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    mapView.invalidate();
                    if(Back==true)
                    {
                        if(first==0)
                        {
                            setContentView(R.layout.activity_main);
                            first++;
                        }
                        ShowMap=false;
                    }
                    if (ShowMap==true)
                    {
                        setContentView(mapView);
                        Back=false;
                    }
                }
            }
        };

        mapView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                switch (event.getKeyCode())
                {
                    case KeyEvent.KEYCODE_B:
                    {
                        Toast.makeText(MainActivity.this,"get1",Toast.LENGTH_SHORT).show();
                        Back = true;
                        break;
                    }
                    case KeyEvent.KEYCODE_D:
                    {
                        Toast.makeText(MainActivity.this,"get2",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return true;
            }
        });

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Index == true) {
                    if (count == 18) {
                        count = 0;
                        if (MoveType == 4) {
                            Index = false;
                        }
                        MoveType = 4;
                        //Index = false;
                    } else {
                        count++;
                        switch (MoveType) {
                            case 1:  //向左运动
                            {
                                pointX = pointX - xSpeed;
                                break;
                            }
                            case 2:  //向右运动
                            {
                                pointX = pointX + xSpeed;
                                break;
                            }
                            case 3:  //向上运动
                            {
                                pointY = pointY - ySpeed;
                                break;
                            }
                            case 4:  //向下运动
                            {
                                pointY = pointY + ySpeed;
                                break;
                            }
                        }
                    }
                } else {

                }
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 200);

    }

    class MapView extends View {

        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map);
        Matrix matrix = new Matrix();

        public MapView(Context context) {
            super(context);
            setFocusable(true);
        }

        public void onDraw(Canvas canvas) {
            matrix.setScale(0.6f, 1.0f);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(bitmap, matrix, null);
            paint.setColor(Color.rgb(255, 0, 0));
            canvas.drawCircle(pointX, pointY, POINT_Size, paint);
        }
    }


    public void MapButtonClick(View view)
    {
        Toast.makeText(MainActivity.this,"get",Toast.LENGTH_SHORT).show();
        ShowMap=true;

    }

    private void initEvent() {
        FirstButtom.setOnClickListener(this);
        SecondButtom.setOnClickListener(this);
        ThirdButtom.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*
            *当View左右滑动的时候触发
             */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                int currentItem = mViewPager.getCurrentItem();
                switch (currentItem) {
                    case 0:
                        resetImage();
                        FirstButtom.setImageResource(R.drawable.ble_pressed);
                        break;
                    case 1:
                        resetImage();
                        SecondButtom.setImageResource(R.drawable.ble_pressed);
                        break;
                    case 2:
                        resetImage();
                        ThirdButtom.setImageResource(R.drawable.ble_pressed);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetImage() {
        FirstButtom.setImageResource(R.drawable.ble_unpressed);
        SecondButtom.setImageResource(R.drawable.ble_unpressed);
        ThirdButtom.setImageResource(R.drawable.ble_unpressed);
    }

    /*
    *初始化Viewpage
     */
    private void initViewPage() {
        //初始化三个布局
        LayoutInflater mLayoutInflater=LayoutInflater.from(this);
        View tab01=mLayoutInflater.inflate(R.layout.viewpage1, null);
        View tab02=mLayoutInflater.inflate(R.layout.viewpage2,null);
        View tab03=mLayoutInflater.inflate(R.layout.viewpage3,null);

        mViews.add(tab01);
        mViews.add(tab02);
        mViews.add(tab03);

        //适配器初始化并配置
        mPagerAdapter=new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }

    /*
    *初始化设置
     */
    private void initView() {
        mViewPager=(ViewPager)findViewById(R.id.id_viewpager);
        /*
        初始化三个界面
         */
        mFirst=(LinearLayout)findViewById(R.id.BLE_first);
        mSecond=(LinearLayout)findViewById(R.id.BLE_second);
        mThird=(LinearLayout)findViewById(R.id.BLE_third);
        /*
        *初始化三个图片按钮
         */
        FirstButtom=(ImageButton)findViewById(R.id.first_image);
        SecondButtom=(ImageButton)findViewById(R.id.second_image);
        ThirdButtom=(ImageButton)findViewById(R.id.third_image);

    }

    /*
    *判断哪个界面需要显示，以及设置显示图片
     */
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()){
            case R.id.first_image:
                mViewPager.setCurrentItem(0);
                resetImage();
                FirstButtom.setImageResource(R.drawable.ble_pressed);
                break;
            case R.id.second_image:
                mViewPager.setCurrentItem(1);
                resetImage();
                SecondButtom.setImageResource(R.drawable.ble_pressed);
                break;
            case R.id.third_image:
                mViewPager.setCurrentItem(2);
                resetImage();
                ThirdButtom.setImageResource(R.drawable.ble_pressed);
                break;
            default:
                break;
        }

    }
}

