package kr.co.mash_up.crema.util;

import android.text.TextUtils;
import android.widget.Toast;

import kr.co.mash_up.crema.CremaApplication;

/**
 * Created by bigstark on 2017. 2. 9..
 */

public class ToastUtil {

    public static void toast(String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }

        Toast.makeText(CremaApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
}
