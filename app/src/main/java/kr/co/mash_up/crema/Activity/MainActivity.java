package kr.co.mash_up.crema.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.co.mash_up.crema.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        startActivity(new Intent(MainActivity.this, SplashActivity.class));

        finish();
    }
}
