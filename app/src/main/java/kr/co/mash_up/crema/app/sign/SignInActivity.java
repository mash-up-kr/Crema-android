package kr.co.mash_up.crema.app.sign;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.bigstark.cycler.CyclerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.app.activity.NearbyCafeActivity;

/**
 * Created by sun on 2017. 1. 25..
 */

public class SignInActivity extends CyclerActivity {

    @BindView(R.id.et_username) EditText username;
    @BindView(R.id.et_password) EditText password;

    @OnClick(R.id.btn_signin)
    void onSignInClicked() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    @OnClick(R.id.btn_login)
    void onLoginClicked() {
        if (already_login()) {
            startActivity(new Intent(SignInActivity.this, NearbyCafeActivity.class));
            finish();
        }

        SharedPreferences settings=getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String name = username.getText().toString();
        String pw = password.getText().toString();

        //todo 서버에서 로그인 쳌쳌
        if (true) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("username", username.getText().toString());
            editor.putString("password", password.getText().toString());

            startActivity(new Intent(SignInActivity.this, NearbyCafeActivity.class));
            finish();
        } else {
            Toast.makeText(SignInActivity.this, "잘못된 아이디와 비밀번호를 입력하셨습니다", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setUnbinder(ButterKnife.bind(this));
    }

    public boolean already_login(){
        SharedPreferences settings=getSharedPreferences("settings", Activity.MODE_PRIVATE);

        String username = settings.getString("username","");
        String password = settings.getString("password","");
        if(username.equals("") || password.equals(""))
            return false;
        else
            return true;
    }
}
