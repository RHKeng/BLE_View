package com.example.user.ble_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by keng on 2016/4/7.
 */
public class MapActivity extends MainActivity{

    private int count = 0;
    private int POINT_Size;
    private int pointX = 380;
    private int pointY = 100;
    private int MoveType = 1;    //定义区分移动a的类型
    private boolean Index = true;  //判断是否发生移动
    private int xSpeed = 7;
    private int ySpeed = 7;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final MapView mapView = new MapView(this);

        POINT_Size = 10;

        setContentView(mapView);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    mapView.invalidate();
                }
            }
        };

        mapView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_B: {
                        Intent intent = new Intent(MapActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case KeyEvent.KEYCODE_A: {
                        Index=true;
                        MoveType=1;
                        break;
                    }
                    case KeyEvent.KEYCODE_S: {
                        Index=true;
                        MoveType=4;
                        break;
                    }
                    case KeyEvent.KEYCODE_D: {
                        Index=true;
                        MoveType=2;
                        break;
                    }
                    case KeyEvent.KEYCODE_W: {
                        Index=true;
                        MoveType=3;
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
                        Index = false;
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
}
