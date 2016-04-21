package com.example.baymax.houseloading;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import android.os.Handler;

public class MainActivity extends AppCompatActivity{

    private HouseLoadingView loadingView;
    private int mProgress = 0;
    private static final int REFRESH_PROGRESS = 0x10;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    if (mProgress < 40) {
                        mProgress += 1;
                        // 随机500ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(200));
                        loadingView.setProgress(mProgress);
                    } else {
                        mProgress += 1;
                        // 随机800ms以内刷新一次
                        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS,
                                new Random().nextInt(300));
                        loadingView.setProgress(mProgress);
                    }
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingView = (HouseLoadingView) findViewById(R.id.house);
        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            mProgress++;
            loadingView.setProgress(mProgress);
            Log.d("test","mProgress : "+mProgress);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
