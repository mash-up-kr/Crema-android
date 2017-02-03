package kr.co.mash_up.crema.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class AccessTokenModel {

    @SerializedName("accessToken")
    private String accessToken;

    public AccessTokenModel(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
