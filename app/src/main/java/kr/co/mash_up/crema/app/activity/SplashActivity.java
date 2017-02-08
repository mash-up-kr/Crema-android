package kr.co.mash_up.crema.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.UserManager;
import kr.co.mash_up.crema.app.sign.SignInActivity;
import teaspoon.annotations.OnUi;

/**
 * Created by sun on 2017. 1. 25..
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        boolean isSigned = UserManager.getInstance().isSigned();

        if (isSigned) {
            startHomeActivity();
        } else {
            startSignInActivity();
        }
    }


    @OnUi(delay = 3000)
    private void startSignInActivity() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }


    @OnUi(delay = 3000)
    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

