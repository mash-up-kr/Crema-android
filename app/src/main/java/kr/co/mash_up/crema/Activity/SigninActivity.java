package kr.co.mash_up.crema.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.co.mash_up.crema.R;

/**
 * Created by sun on 2017. 1. 25..
 */

public class SigninActivity extends AppCompatActivity{

    EditText username;
    EditText password;
    EditText passwordcheck;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        init();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 회원가입
            }
        });
    }

    public void init(){
        username = (EditText)findViewById(R.id.et_username);
        password = (EditText)findViewById(R.id.et_password);
        passwordcheck = (EditText)findViewById(R.id.et_passwordcheck);
        signin = (Button)findViewById(R.id.btn_signin);
    }
}
