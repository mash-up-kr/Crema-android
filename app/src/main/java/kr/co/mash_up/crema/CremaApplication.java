package kr.co.mash_up.crema;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

import teaspoon.TeaSpoon;

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

        Hawk.init(this).build();
        TeaSpoon.initialize();
    }
}
