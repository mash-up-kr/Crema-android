package kr.co.mash_up.crema;

import com.orhanobut.hawk.Hawk;

import kr.co.mash_up.crema.model.user.UserModel;
import kr.co.mash_up.crema.util.Defines;

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

    public void saveAccessToken(String accessToken) {
        Hawk.put(Defines.HAWK_KEY_ACCESS_TOKEN, accessToken);
    }


    public void saveMe(UserModel me) {
        Hawk.put(Defines.HAWK_KEY_ME, me);
    }


    public boolean isSigned() {
        return Hawk.contains(Defines.HAWK_KEY_ME);
    }


    public String getAccessToken() {
        return Hawk.get(Defines.HAWK_KEY_ACCESS_TOKEN, "");
    }


    public UserModel getMe() {
        return Hawk.get(Defines.HAWK_KEY_ME, null);
    }


    public void clear() {
        Hawk.delete(Defines.HAWK_KEY_ME);
    }

}
