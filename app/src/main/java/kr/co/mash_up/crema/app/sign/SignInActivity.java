package kr.co.mash_up.crema.app.sign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.bigstark.cycler.CyclerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.UserManager;
import kr.co.mash_up.crema.model.error.ErrorModel;
import kr.co.mash_up.crema.model.user.AccessTokenModel;
import kr.co.mash_up.crema.model.user.UserModel;
import kr.co.mash_up.crema.model.user.command.UserLoginCommand;
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

public class SignInActivity extends CyclerActivity {

    @BindView(R.id.et_sign_in_email) EditText etEmail;
    @BindView(R.id.et_sign_in_password) EditText etPassword;

    @OnClick(R.id.btn_sign_in_sign_up)
    void onSignUpClicked() {
        Intent intent = new Intent(Defines.INTENT_SIGN_UP_ACTIVITY);
        startActivity(intent);
    }

    @OnClick(R.id.btn_sign_in_login)
    void onLoginClicked() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            ToastUtil.toast("이메일을 입력해주세요.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast("패스워드를 입력해주세요.");
            return;
        }


        UserLoginCommand command = new UserLoginCommand(email, password);

        CremaClient.getService(UserService.class)
                .login(command)
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
                        UserManager.getInstance().saveAccessToken(accessTokenModel);

                        getMe();
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_main);
        setUnbinder(ButterKnife.bind(this));
    }


    private void getMe() {
        UserManager.getInstance().getMe(new UserManager.MeCallback() {
            @Override
            public void onSuccess2getMe(UserModel user) {
                // start home activity

                Intent intent = new Intent(Defines.INTENT_HOME_ACTIVITY);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFail2getMe(ErrorModel error) {
                // TODO 에러 처리
            }
        });
    }
}
