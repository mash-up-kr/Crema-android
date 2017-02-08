package kr.co.mash_up.crema.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.util.Defines;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(Defines.INTENT_SPLASH_ACTIVITY);
        startActivity(intent);

        finish();
    }
}
