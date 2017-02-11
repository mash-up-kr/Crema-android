package kr.co.mash_up.crema.app.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.bigstark.cycler.CyclerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.model.user.AccessTokenModel;
import kr.co.mash_up.crema.model.user.command.UserRegisterCommand;
import kr.co.mash_up.crema.rest.CremaClient;
import kr.co.mash_up.crema.rest.user.UserService;
import kr.co.mash_up.crema.util.Defines;
import kr.co.mash_up.crema.util.ToastUtil;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sun on 2017. 1. 25..
 */

public class SignUpActivity extends CyclerActivity {

    @BindView(R.id.et_sign_up_email) EditText etEmail;
    @BindView(R.id.et_sign_up_password) EditText etPassword;
    @BindView(R.id.et_sign_up_password_check) EditText etPasswordCheck;
    @BindView(R.id.btn_sign_up_sign_up) Button btnSingUp;

    @OnClick(R.id.btn_sign_up_sign_up)
    void onSignUpClicked(){
        String email = etEmail.getText().toString();
        String pw = etPassword.getText().toString();
        String pwCheck = etPasswordCheck.getText().toString();

        if(TextUtils.isEmpty(email)){
            ToastUtil.toast("이메일을 입력해주세요.");
            return;
        }
        if (TextUtils.isEmpty(pw)){
            ToastUtil.toast("비밀번호를 입력해주세요.");
            return;
        }
        if (TextUtils.isEmpty(pwCheck)){
            ToastUtil.toast("비밀번호 확인을 입력해주세요.");
            return;
        }
        if(!pw.equals(pwCheck)){
            ToastUtil.toast("비밀번호와 비밀번호 확인을 일치시켜 주세요.");
            return;
        }

        UserRegisterCommand command = new UserRegisterCommand(email, pw);

        CremaClient.getService(UserService.class)
                .register(command)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AccessTokenModel>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) { // something error
                        // TODO 에러 처리
                    }

                    @Override
                    public void onNext(AccessTokenModel accessTokenModel) { // success
                        // save access token. It is used in authorization

                        Intent intent = new Intent(Defines.INTENT_SIGN_IN_ACTIVITY);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_main);
        setUnbinder(ButterKnife.bind(this));
    }
}
