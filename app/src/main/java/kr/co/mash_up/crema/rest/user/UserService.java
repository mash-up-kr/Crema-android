package kr.co.mash_up.crema.rest.user;

import kr.co.mash_up.crema.model.user.AccessTokenModel;
import kr.co.mash_up.crema.model.user.UserModel;
import kr.co.mash_up.crema.model.user.command.UserLoginCommand;
import kr.co.mash_up.crema.model.user.command.UserRegisterCommand;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by bigstark on 2017. 2. 3..
 */

public interface UserService {

    @POST("user/register")
    Observable<AccessTokenModel> register(@Body UserRegisterCommand command);


    @POST("user/login")
    Observable<AccessTokenModel> login(@Body UserLoginCommand command);


    @GET("user/me")
    Observable<UserModel> getMe();

}
