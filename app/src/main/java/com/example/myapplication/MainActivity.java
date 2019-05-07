package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.ep_video_use.EditActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CountDownTimerImp countDownTimerImp = new CountDownTimerImp(6000500, 1000);
//        countDownTimerImp.setOnTime(new CountDownTimerImp.OnTime() {
//            @Override
//            public void onTime(@NotNull String time) {
//                Log.e(TAG, "onTime: " + time);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.e(TAG, "onFinish: " + "finish");
//            }
//        });
//        countDownTimerImp.start();

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });


    }
}
