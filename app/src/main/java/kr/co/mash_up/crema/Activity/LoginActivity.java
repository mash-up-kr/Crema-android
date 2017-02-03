package kr.co.mash_up.crema.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.co.mash_up.crema.R;

/**
 * Created by sun on 2017. 1. 25..
 */

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button login;
    Button signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();

        final SharedPreferences settings=getSharedPreferences("settings", Activity.MODE_PRIVATE);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SigninActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(already_login()){
                    startActivity(new Intent(LoginActivity.this, NearbyCafeActivity.class));
                    finish();
                }

                //todo 서버에서 로그인 쳌쳌
                if(true){
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", username.getText().toString());
                    editor.putString("password", password.getText().toString());

                    startActivity(new Intent(LoginActivity.this, NearbyCafeActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "잘못된 아이디와 비밀번호를 입력하셨습니다", Toast.LENGTH_LONG).show();
                }
            }
        });


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

    public void init(){
        username = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);
        login = (Button)findViewById(R.id.btn_login);
        signin = (Button)findViewById(R.id.btn_signin);
    }
}
