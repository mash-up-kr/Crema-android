package kr.co.mash_up.crema.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import kr.co.mash_up.crema.R;

/**
 * Created by sun on 2017. 1. 25..
 */

public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }
}

