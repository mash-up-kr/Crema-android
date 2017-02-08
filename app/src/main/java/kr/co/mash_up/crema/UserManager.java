package kr.co.mash_up.crema;

import com.orhanobut.hawk.Hawk;

import kr.co.mash_up.crema.model.error.ErrorModel;
import kr.co.mash_up.crema.model.user.AccessTokenModel;
import kr.co.mash_up.crema.model.user.UserModel;
import kr.co.mash_up.crema.rest.CremaClient;
import kr.co.mash_up.crema.rest.user.UserService;
import kr.co.mash_up.crema.util.Defines;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bigstark on 2017. 2. 3..
 */

public class UserManager {

    private volatile static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }

        return instance;
    }

    public void saveAccessToken(AccessTokenModel accessToken) {
        Hawk.put(Defines.HAWK_KEY_ACCESS_TOKEN, accessToken);
    }


    public void getMe(final MeCallback callback) {
        CremaClient.getService(UserService.class)
                .getMe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<UserModel>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) {
                            callback.onFail2getMe(null);
                        }
                    }

                    @Override
                    public void onNext(UserModel userModel) {
                        saveMe(userModel);
                        if (callback != null) {
                            callback.onSuccess2getMe(userModel);
                        }
                    }
                });
    }


    private void saveMe(UserModel me) {
        Hawk.put(Defines.HAWK_KEY_ME, me);
    }


    public boolean isSigned() {
        return Hawk.contains(Defines.HAWK_KEY_ME);
    }


    public String getAccessToken() {
        if (Hawk.contains(Defines.HAWK_KEY_ACCESS_TOKEN)) {
            return ((AccessTokenModel) Hawk.get(Defines.HAWK_KEY_ACCESS_TOKEN)).getAccessToken();
        }

        return "";
    }


    public UserModel getMe() {
        return Hawk.get(Defines.HAWK_KEY_ME, null);
    }


    public void clear() {
        Hawk.delete(Defines.HAWK_KEY_ME);
    }


    public interface MeCallback {

        public void onSuccess2getMe(UserModel user);

        public void onFail2getMe(ErrorModel error);
    }
}
