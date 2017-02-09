package kr.co.mash_up.crema.app.sign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.co.mash_up.crema.R;

/**
 * Created by sun on 2017. 1. 25..
 */

public class SignUpActivity extends AppCompatActivity{

    EditText username;
    EditText password;
    EditText passwordcheck;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_main);

        init();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 회원가입
            }
        });
    }

    public void init(){
        username = (EditText)findViewById(R.id.et_sign_in_email);
        password = (EditText)findViewById(R.id.et_sign_in_password);
        passwordcheck = (EditText)findViewById(R.id.et_password_check);
        signin = (Button)findViewById(R.id.btn_sign_in_sign_up);

        //
    }
}
