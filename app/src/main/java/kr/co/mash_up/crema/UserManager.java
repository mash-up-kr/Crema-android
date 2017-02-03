package kr.co.mash_up.crema;

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


    public String getAccessToken() {
        return "";
    }

}
