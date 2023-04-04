package com.boxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.boxuegu.MainActivity;
import com.boxuegu.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        init();
    }

    private void init() {
        TextView tv_version = findViewById(R.id.tv_version);
        try {
            //获取程序包信息
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            tv_version.setText("V" + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("V");
        }
        //利用Timer让此界面延迟3秒后再跳转，timer中有一个线程，这个线程不断执行task
        Timer timer = new Timer();
        //timertask实现runnable接口，TimerTask类表示一个在指定时间内执行的task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task, 3000);
    }
}