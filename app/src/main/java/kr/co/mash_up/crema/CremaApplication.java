package kr.co.mash_up.crema;

import android.app.Application;

/**
 * Created by bigstark on 2017. 2. 3..
 */

public class CremaApplication extends Application {

    private static CremaApplication instance;


    public static CremaApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
