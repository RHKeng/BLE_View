package com.example.user.ble_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements android.view.View.OnClickListener {

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

        setContentView(R.layout.activity_main);
        initView();
        initViewPage();
        initEvent();
    }




    public void MapButtonClick(View view)
    {
        Toast.makeText(MainActivity.this,"get",Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(MainActivity.this,MapActivity.class);
        startActivity(intent);
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

