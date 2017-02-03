package kr.co.mash_up.crema.model.user.command;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class UserLoginCommand {

    @SerializedName("email")
    private String email;

    @SerializedName("pw")
    private String pw;


    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }

    @Override
    public String toString() {
        return "UserLoginCommand{" +
                "email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                '}';
    }

}
