package com.example.sgajraj.androidchathead;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

import receiver.LockScreenReceiver;

public class ChatHeadService extends Service {

    BroadcastReceiver mReceiver;
    private WindowManager windowManager;
    private ImageView chatHead;

    boolean mHasDoubleClicked = false;
    long lastPressTime;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override public void onCreate() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new LockScreenReceiver();
        registerReceiver(mReceiver, filter);

        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.music);
        chatHead.setVisibility(View.VISIBLE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                200,
                200,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private WindowManager.LayoutParams paramsF = params;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        long pressTime = System.currentTimeMillis();


                        // If double click...
                        if (pressTime - lastPressTime <= 300) {
                            //createNotification();
                            //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/")));
                            //finish();
                            final PackageManager pm = getPackageManager();
                            //get a list of installed apps.
                            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

                            for (ApplicationInfo packageInfo : packages) {
                                Log.d("ChatHeadService", "Installed package :" + packageInfo.packageName);
                                Log.d("ChatHeadService", "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                                if (pm.getLaunchIntentForPackage(packageInfo.packageName) != null){
                                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(packageInfo.packageName);
                                    startActivity(LaunchIntent);
                                    break;
                                }
                            }

                            ChatHeadService.this.stopSelf();
                            mHasDoubleClicked = true;
                        }
                        else {     // If not double click....
                            mHasDoubleClicked = false;
                        }
                        lastPressTime = pressTime;
                        initialX = paramsF.x;
                        initialY = paramsF.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                        paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(chatHead, paramsF);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(chatHead, params);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        if (chatHead != null) windowManager.removeView(chatHead);
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
